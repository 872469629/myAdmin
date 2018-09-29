/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import cn.gleme.*;
import cn.gleme.plugin.StoragePlugin;
import cn.gleme.service.FileService;
import cn.gleme.service.PluginService;
import cn.gleme.util.FreeMarkerUtils;
import cn.gleme.util.SystemUtils;
import cn.gleme.FileType;
import cn.gleme.Setting;
import cn.gleme.plugin.StoragePlugin;
import cn.gleme.service.FileService;
import cn.gleme.service.PluginService;
import cn.gleme.util.FreeMarkerUtils;
import cn.gleme.util.SystemUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.expression.TypeComparator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import freemarker.template.TemplateException;

/**
 * Service - 文件
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Service("fileServiceImpl")
public class FileServiceImpl implements FileService, ServletContextAware {

	/** ServletContext */
	private ServletContext servletContext;

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;

	/**
	 * 设置ServletContext
	 * 
	 * @param servletContext
	 *            ServletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 添加文件上传任务
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param path
	 *            上传路径
	 * @param file
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	private void addUploadTask(final StoragePlugin storagePlugin, final String path, final File file, final String contentType) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				upload(storagePlugin, path, file, contentType);
			}
		});
	}

	/**
	 * 上传文件
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param path
	 *            上传路径
	 * @param file
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	private void upload(StoragePlugin storagePlugin, String path, File file, String contentType) {
		Assert.notNull(storagePlugin);
		Assert.hasText(path);
		Assert.notNull(file);
		Assert.hasText(contentType);

		try {
			storagePlugin.upload(path, file, contentType);
		} finally {
			FileUtils.deleteQuietly(file);
		}
	}
	

	public boolean isValid(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		Setting setting = SystemUtils.getSetting();
		if (setting.getUploadMaxSize() != null && setting.getUploadMaxSize() != 0 && multipartFile.getSize() > setting.getUploadMaxSize() * 1024L * 1024L) {
			return false;
		}
		String[] uploadExtensions;
		switch (fileType) {
		case media:
			uploadExtensions = setting.getUploadMediaExtensions();
			break;
		case file:
			uploadExtensions = setting.getUploadFileExtensions();
			break;
		default:
			uploadExtensions = setting.getUploadImageExtensions();
			break;
		}
		if (ArrayUtils.isNotEmpty(uploadExtensions)) {
//			return FilenameUtils.isExtension(multipartFile.getOriginalFilename(), uploadExtensions);
			return true;
		}
		return false;
	}

	public String upload(FileType fileType, MultipartFile multipartFile, boolean async) {
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		if(fileType==null){
			fileType = FileType.file;
		}
//		switch (fileType) {
//		case media:
//			uploadPath = setting.getMediaUploadPath();
//			break;
//		case file:
//			uploadPath = setting.getFileUploadPath();
//			break;
//		default:
//			uploadPath = setting.getImageUploadPath();
//			break;
//		}
		uploadPath = setting.getFileUploadPath();
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreeMarkerUtils.process(uploadPath, model);
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");
				multipartFile.transferTo(tempFile);
				String contentType = multipartFile.getContentType();
				if (async) {
					addUploadTask(storagePlugin, destPath, tempFile, contentType);
				} else {
					upload(storagePlugin, destPath, tempFile, contentType);
				}
				return storagePlugin.getUrl(destPath);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}


	/**
	 * 抓取网络资源到空间
	 * @param url
	 */
	public String fetch(String url) {
		Assert.notNull(url);
		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		uploadPath = setting.getFileUploadPath();
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreeMarkerUtils.process(uploadPath, model);
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(url);
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				storagePlugin.fetch(url,destPath);
				return storagePlugin.getUrl(destPath);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}

	public String upload(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		return upload(fileType, multipartFile, true);
	}
	
	@Override
	public String upload(FileType fileType, InputStream is, boolean async) {
		Assert.notNull(is);

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		if(fileType==null){
			fileType = FileType.file;
		}
		uploadPath = setting.getFileUploadPath();
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreeMarkerUtils.process(uploadPath, model);
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(System.currentTimeMillis()+".png");
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");
//				multipartFile.transferTo(tempFile);
//				String contentType = multipartFile.getContentType();
				if (async) {
					taskExecutor.execute(new Runnable() {
						public void run() {
							Assert.notNull(storagePlugin);
							Assert.hasText(path);
							Assert.notNull(is);

							try {
								storagePlugin.upload(path, is, destPath);
							} finally {
//								FileUtils.deleteQuietly(file);
							}
						}
					});
				} else {
					Assert.notNull(storagePlugin);
					Assert.hasText(path);
					Assert.notNull(is);

					try {
						storagePlugin.upload(path, is, destPath);
					} finally {
//						FileUtils.deleteQuietly(file);
					}
				}
				return storagePlugin.getUrl(destPath);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}

	public String uploadLocal(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		switch (fileType) {
		case media:
			uploadPath = setting.getMediaUploadPath();
			break;
		case file:
			uploadPath = setting.getFileUploadPath();
			break;
		default:
			uploadPath = setting.getImageUploadPath();
			break;
		}
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreeMarkerUtils.process(uploadPath, model);
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			File destFile = new File(servletContext.getRealPath(destPath));
			new File(path).mkdirs();
			multipartFile.transferTo(destFile);
			return destPath;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}


	public List<FileInfo> browser(String path,Pageable pageable, FileType fileType, FileInfo.OrderType orderType) {
		if (path != null) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (!path.endsWith("/")) {
				path += "/";
			}
		} else {
			path = "/";
		}
//		path = "/"+path;
		Setting setting = SystemUtils.getSetting();
		String uploadPath;
//		if (fileType == FileType.media) {
//			uploadPath = setting.getMediaUploadPath();
//		} else if (fileType == FileType.file) {
//			uploadPath = setting.getFileUploadPath();
//		} else {
//			uploadPath = setting.getImageUploadPath();
//		}
		uploadPath = setting.getFileUploadPath();
		String browsePath = StringUtils.substringBefore(uploadPath, "${");
		browsePath = StringUtils.substringBeforeLast(browsePath, "/") + path;

		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		if (browsePath.indexOf("..") >= 0) {
			return fileInfos;
		}
		for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
			fileInfos = storagePlugin.browser(browsePath,pageable);
			break;
		}
		if (orderType == FileInfo.OrderType.size) {
			Collections.sort(fileInfos, new SizeComparator());
		} else if (orderType == FileInfo.OrderType.type) {
			Collections.sort(fileInfos, new TypeComparator());
		} else {
			Collections.sort(fileInfos, new NameComparator());
		}
		return fileInfos;
	}

	public void delete(String[] ids) {
		Assert.notNull(ids);
		for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
			storagePlugin.delete(ids);
		}
	}

	private class NameComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getFilename(), fileInfos2.getFilename()).toComparison();
		}
	}

	private class SizeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getSize(), fileInfos2.getSize()).toComparison();
		}
	}

	private class TypeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(FilenameUtils.getExtension(fileInfos1.getFilename()), FilenameUtils.getExtension(fileInfos2.getFilename())).toComparison();
		}
	}


}