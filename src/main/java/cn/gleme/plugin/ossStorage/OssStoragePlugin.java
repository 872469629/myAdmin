/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.plugin.ossStorage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.gleme.FileInfo;
import cn.gleme.Page;
import cn.gleme.Pageable;
import cn.gleme.entity.PluginConfig;
import cn.gleme.plugin.StoragePlugin;
import cn.gleme.entity.PluginConfig;
import cn.gleme.plugin.StoragePlugin;

import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * Plugin - 阿里云存储
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Component("ossStoragePlugin")
public class OssStoragePlugin extends StoragePlugin {

	@Override
	public String getName() {
		return "阿里云存储";
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
		return "oss_storage/install.jhtml";
	}

	@Override
	public String getUninstallUrl() {
		return "oss_storage/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl() {
		return "oss_storage/setting.jhtml";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String endpoint = pluginConfig.getAttribute("endpoint");
			String accessId = pluginConfig.getAttribute("accessId");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			InputStream inputStream = null;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(file));
				OSSClient ossClient = new OSSClient(endpoint, accessId, accessKey);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.length());
				ossClient.putObject(bucketName, StringUtils.removeStart(path, "/"), inputStream, objectMetadata);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	@Override
	public String getUrl(String path) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String url = pluginConfig.getAttribute("url");
			return url + path;
		}
		return null;
	}

	@Override
	public List<FileInfo> browser(String path,Pageable pageable) {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String accessId = pluginConfig.getAttribute("accessId");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			String url = pluginConfig.getAttribute("url");
			try {
				OSSClient ossClient = new OSSClient(accessId, accessKey);
				ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
				listObjectsRequest.setPrefix(StringUtils.removeStart(path, "/"));
				listObjectsRequest.setDelimiter("/");
				ObjectListing objectListing = ossClient.listObjects(listObjectsRequest);
				for (String commonPrefix : objectListing.getCommonPrefixes()) {
					FileInfo fileInfo = new FileInfo();
					fileInfo.setFilename(StringUtils.substringAfterLast(StringUtils.removeEnd(commonPrefix, "/"), "/"));
					fileInfo.setUrl(url + "/" + commonPrefix);
					fileInfo.setIsDirectory(true);
					fileInfo.setSize(0L);
					fileInfos.add(fileInfo);
				}
				for (OSSObjectSummary ossObjectSummary : objectListing.getObjectSummaries()) {
					if (ossObjectSummary.getKey().endsWith("/")) {
						continue;
					}
					FileInfo fileInfo = new FileInfo();
					fileInfo.setFilename(StringUtils.substringAfterLast(ossObjectSummary.getKey(), "/"));
					fileInfo.setUrl(url + "/" + ossObjectSummary.getKey());
					fileInfo.setIsDirectory(false);
					fileInfo.setSize(ossObjectSummary.getSize());
					fileInfo.setLastModified(ossObjectSummary.getLastModified());
					fileInfos.add(fileInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileInfos;
	}

	@Override
	public void delete(String[] urls) {

	}

	@Override
	public void fetch(String url, String destpath) {

	}

	@Override
	public void upload(String path, InputStream fileInputStream, String contentType) {
		// TODO Auto-generated method stub
		
	}

}