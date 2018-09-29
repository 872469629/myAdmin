/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.service;

import cn.gleme.FileInfo;
import cn.gleme.FileType;
import cn.gleme.Pageable;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Service - 文件
 * 
 * @author XJANY Team
 * @version 4.0
 */
public interface FileService {

	/**
	 * 文件验证
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 文件验证是否通过
	 */
	boolean isValid(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件上传
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @param async
	 *            是否异步
	 * @return 访问URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile, boolean async);

	/**
	 * 抓取网络资源到空间
	 * @param url
	 */
	String fetch(String url);

	/**
	 * 文件上传(异步)
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 访问URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件上传至本地(同步)
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 路径
	 */
	String uploadLocal(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件浏览
	 *
	 * @param path
	 *            浏览路径
	 * @param fileType
	 *            文件类型
	 * @param orderType
	 *            排序类型
	 * @return 文件信息
	 */
	List<FileInfo> browser(String path, Pageable pageable, FileType fileType, FileInfo.OrderType orderType);

	/**
	 * 上传文件
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param path
	 *            上传路径
	 * @param InputStream
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	String upload(FileType fileType, InputStream is, boolean async);


	public void delete(String[] ids);
}