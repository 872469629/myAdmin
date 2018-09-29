/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.plugin.ftpStorage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import cn.gleme.FileInfo;
import cn.gleme.Page;
import cn.gleme.Pageable;
import cn.gleme.entity.PluginConfig;
import cn.gleme.plugin.StoragePlugin;
import cn.gleme.entity.PluginConfig;
import cn.gleme.plugin.StoragePlugin;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

/**
 * Plugin - FTP存储
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Component("ftpStoragePlugin")
public class FtpStoragePlugin extends StoragePlugin {

	@Override
	public String getName() {
		return "FTP存储";
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
		return "ftp_storage/install.jhtml";
	}

	@Override
	public String getUninstallUrl() {
		return "ftp_storage/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl() {
		return "ftp_storage/setting.jhtml";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String host = pluginConfig.getAttribute("host");
			Integer port = Integer.valueOf(pluginConfig.getAttribute("port"));
			String username = pluginConfig.getAttribute("username");
			String password = pluginConfig.getAttribute("password");
			FTPClient ftpClient = new FTPClient();
			InputStream inputStream = null;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(file));
				ftpClient.connect(host, port);
				ftpClient.login(username, password);
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
					String directory = StringUtils.substringBeforeLast(path, "/");
					String filename = StringUtils.substringAfterLast(path, "/");
					if (!ftpClient.changeWorkingDirectory(directory)) {
						String[] paths = StringUtils.split(directory, "/");
						String p = "/";
						ftpClient.changeWorkingDirectory(p);
						for (String s : paths) {
							p += s + "/";
							if (!ftpClient.changeWorkingDirectory(p)) {
								ftpClient.makeDirectory(s);
								ftpClient.changeWorkingDirectory(p);
							}
						}
					}
					ftpClient.storeFile(filename, inputStream);
					ftpClient.logout();
				}
			} catch (SocketException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(inputStream);
				try {
					if (ftpClient.isConnected()) {
						ftpClient.disconnect();
					}
				} catch (IOException e) {
				}
			}
		}
	}

	@Override
	public String getUrl(String path) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			return urlPrefix + path;
		}
		return null;
	}

	@Override
	public List<FileInfo> browser(String path,Pageable pageable) {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String host = pluginConfig.getAttribute("host");
			Integer port = Integer.valueOf(pluginConfig.getAttribute("port"));
			String username = pluginConfig.getAttribute("username");
			String password = pluginConfig.getAttribute("password");
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			FTPClient ftpClient = new FTPClient();
			try {
				ftpClient.connect(host, port);
				ftpClient.login(username, password);
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()) && ftpClient.changeWorkingDirectory(path)) {
					for (FTPFile ftpFile : ftpClient.listFiles()) {
						FileInfo fileInfo = new FileInfo();
						fileInfo.setFilename(ftpFile.getName());
						fileInfo.setUrl(urlPrefix + path + ftpFile.getName());
						fileInfo.setIsDirectory(ftpFile.isDirectory());
						fileInfo.setSize(ftpFile.getSize());
						fileInfo.setLastModified(ftpFile.getTimestamp().getTime());
						fileInfos.add(fileInfo);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					} catch (IOException e) {
					}
				}
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