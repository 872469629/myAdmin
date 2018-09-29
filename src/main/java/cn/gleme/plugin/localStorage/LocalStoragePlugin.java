/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.plugin.localStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import cn.gleme.*;
import cn.gleme.plugin.StoragePlugin;
import cn.gleme.util.SystemUtils;
import cn.gleme.Setting;
import cn.gleme.plugin.StoragePlugin;
import cn.gleme.util.SystemUtils;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * Plugin - 本地文件存储
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Component("localStoragePlugin")
public class LocalStoragePlugin extends StoragePlugin implements ServletContextAware {

	/** ServletContext */
	private ServletContext servletContext;

	/**
	 * 设置ServletContext
	 * 
	 * @param servletContext
	 *            ServletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public String getName() {
		return "本地文件存储";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "XJANY";
	}

	@Override
	public String getSiteUrl() {
		return "http://www.gleme.cn";
	}

	@Override
	public String getInstallUrl() {
		return "local_storage/install.jhtml";
	}

	@Override
	public String getUninstallUrl() {
		return "local_storage/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl() {
		return "local_storage/setting.jhtml";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		File destFile = new File(servletContext.getRealPath(path));
		try {
			FileUtils.moveFile(file, destFile);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public String getUrl(String path) {
		Setting setting = SystemUtils.getSetting();
		return setting.getSiteUrl() + path;
	}

	@Override
	public List<FileInfo> browser(String path,Pageable pageable) {
		Setting setting = SystemUtils.getSetting();
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		File directory = new File(servletContext.getRealPath(path));
		if (directory.exists() && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if(file.isDirectory()){
					for (File filechild : file.listFiles()) {
						FileInfo fileInfo = new FileInfo();
						fileInfo.setFilename(filechild.getName());
						fileInfo.setUrl(setting.getSiteUrl() + path + file.getName() +"/"+ filechild.getName());
						fileInfo.setIsDirectory(filechild.isDirectory());
						fileInfo.setSize(filechild.length());
						fileInfo.setLastModified(new Date(filechild.lastModified()));
						fileInfos.add(fileInfo);
					}
				}else{
					FileInfo fileInfo = new FileInfo();
					fileInfo.setFilename(file.getName());
					fileInfo.setUrl(setting.getSiteUrl() + path + file.getName());
					fileInfo.setIsDirectory(file.isDirectory());
					fileInfo.setSize(file.length());
					fileInfo.setLastModified(new Date(file.lastModified()));
					fileInfos.add(fileInfo);
				}
			}
		}
		return fileInfos;
	}

	@Override
	public void upload(String path, InputStream fileInputStream, String contentType) {
		// TODO Auto-generated method stub
		
	}

	public void delete(String[] urls) {
		for (String url : urls) {
			File destFile = new File(servletContext.getRealPath(url));
			if (destFile.isDirectory()) {
				try {
					FileUtils.deleteDirectory(destFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (destFile.isFile()) {
				destFile.delete();
			}
		}
	}

	@Override
	public void fetch(String url, String destpath) {

	}
}