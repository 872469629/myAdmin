/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.plugin.qiniuStorage;

import cn.gleme.FileInfo;
import cn.gleme.Page;
import cn.gleme.Pageable;
import cn.gleme.entity.PluginConfig;
import cn.gleme.plugin.StoragePlugin;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Plugin - 七牛云存储
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Component("qiniuStoragePlugin")
public class QiniuStoragePlugin extends StoragePlugin {

	@Override
	public String getName() {
		return "七牛云存储";
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
		return "qiniu_storage/install.jhtml";
	}

	@Override
	public String getUninstallUrl() {
		return "qiniu_storage/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl() {
		return "qiniu_storage/setting.jhtml";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String secretKey = pluginConfig.getAttribute("secretKey");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			Auth auth = Auth.create(accessKey, secretKey);
			String upToken = auth.uploadToken(bucketName);
			Zone z = Zone.autoZone();
			Configuration c = new Configuration(z);
			UploadManager uploadManager = new UploadManager(c);
			try {
				uploadManager.put(file,urlPrefix+path,upToken);
			} catch (QiniuException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void upload(String path, InputStream fileInputStream, String contentType) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String secretKey = pluginConfig.getAttribute("secretKey");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			Auth auth = Auth.create(accessKey, secretKey);
			String upToken = auth.uploadToken(bucketName);
			Zone z = Zone.autoZone();
			Configuration c = new Configuration(z);
			UploadManager uploadManager = new UploadManager(c);
			try {
				Response put = uploadManager.put(fileInputStream,urlPrefix+contentType,upToken,null,null);
				System.out.println(urlPrefix+contentType);
			} catch (QiniuException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String getUrl(String path) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String url = pluginConfig.getAttribute("url");
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			return url+"/"+ urlPrefix + path;
		}
		return null;
	}

	@Override
	public List<FileInfo> browser(String path,Pageable pageable) {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String secretKey = pluginConfig.getAttribute("secretKey");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			String url = pluginConfig.getAttribute("url");
			try {
				Auth auth = Auth.create(accessKey, secretKey);
				Configuration cfg = new Configuration(Zone.zone0());
				BucketManager bucketManager = new BucketManager(auth, cfg);
				int limit = 100;
				String delimiter = "";

				FileListing fileListing = bucketManager.listFiles(bucketName,urlPrefix+path,pageable.getSearchProperty(),limit,delimiter);
				pageable.setSearchProperty(fileListing.marker);
				if(fileListing.items != null){
					for (com.qiniu.storage.model.FileInfo item : fileListing.items) {
						FileInfo fileInfo = new FileInfo();
						fileInfo.setFilename(StringUtils.substringAfterLast(StringUtils.removeEnd(item.key, "/"), "/"));
						fileInfo.setUrl(url+"/" + item.key);
						fileInfo.setIsDirectory(true);
						fileInfo.setSize(0L);
						fileInfos.add(fileInfo);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileInfos;
	}

	public void delete(String[] urls){
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String secretKey = pluginConfig.getAttribute("secretKey");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			String urlfix = pluginConfig.getAttribute("url");
			Auth auth = Auth.create(accessKey, secretKey);
			Configuration cfg = new Configuration(Zone.zone0());
			BucketManager bucketManager = new BucketManager(auth, cfg);
			for (String url : urls) {
				try {
					bucketManager.delete(bucketName, url.replaceAll(urlfix+"/",""));
				} catch (QiniuException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 抓取网络资源到空间
	 * @param url
	 */
	public void fetch(String url,String destpath) throws QiniuException {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String secretKey = pluginConfig.getAttribute("secretKey");
			String accessKey = pluginConfig.getAttribute("accessKey");
			String bucketName = pluginConfig.getAttribute("bucketName");
			String urlfix = pluginConfig.getAttribute("url");
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			Auth auth = Auth.create(accessKey, secretKey);
			Configuration cfg = new Configuration(Zone.zone0());
			BucketManager bucketManager = new BucketManager(auth, cfg);

			//抓取网络资源到空间
			DefaultPutRet fetchRet = bucketManager.fetch(url, bucketName, urlPrefix+destpath);
		}
	}

}