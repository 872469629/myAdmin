/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.gleme.*;
import cn.gleme.service.FileService;
import cn.gleme.FileType;
import cn.gleme.Message;

import cn.gleme.util.ResponseUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller - 文件
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Controller("adminFileController")
@RequestMapping("/admin/file")
public class FileController extends BaseController {

	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	/**
	 * 上传
	 *
	 *
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> upload(FileType fileType, MultipartFile file) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (fileType == null || file == null || file.isEmpty()) {
			data.put("message", ERROR_MESSAGE);
			data.put("state", "FAIL");
			return data;
		}
		if (!fileService.isValid(fileType, file)) {
			data.put("message", Message.warn("admin.upload.invalid"));
			data.put("state", "FAIL");
			return data;
		}
		String url = fileService.upload(fileType, file, false);
		if (StringUtils.isEmpty(url)) {
			data.put("message", Message.warn("admin.upload.error"));
			data.put("state", "FAIL");
			return data;
		}
		data.put("message", SUCCESS_MESSAGE);
		data.put("state", "SUCCESS");
		data.put("is_image", 1);
		data.put("url", url);
		return data;
	}

	/**
	 * 抓取网络资源到空间
	 *
	 *
	 */
	@RequestMapping(value = "/fetch", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> fetch(String url) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (url == null) {
			data.put("message", ERROR_MESSAGE);
			data.put("state", "FAIL");
			return data;
		}
		String fetchUrl = fileService.fetch(url);
		if (StringUtils.isEmpty(fetchUrl)) {
			data.put("message", Message.warn("admin.upload.error"));
			data.put("state", "FAIL");
			return data;
		}
		data.put("message", SUCCESS_MESSAGE);
		data.put("state", "SUCCESS");
		data.put("is_image", 1);
		data.put("url", fetchUrl);
		return data;
	}

	/**
	 * 浏览
	 */
	@RequestMapping(value = "/browser", method = RequestMethod.GET)
	public @ResponseBody
	void browser(HttpServletResponse response,String path,String page, FileType fileType, FileInfo.OrderType orderType) {
		JSONObject jsonObject = new JSONObject();
		if("00".equals(path)){
			path = "";
		}
		if("1".equals(page)){
			page = "";
		}

		Pageable pageable = new Pageable();
		pageable.setSearchProperty(page);
		jsonObject.put("errno",0) ;
		jsonObject.put("items",fileService.browser(path, pageable, fileType, orderType)) ;
//		jsonObject.put("pager","<div><ul class=\"pagination pagination-centered\"><li class=\"active\"><a href=\"javascript:;\">1</a></li><li><a href=\"javascript:;\" page=\"2\" >2</a></li><li><a href=\"javascript:;\" page=\"2\"  class=\"pager-nav\">下一页&raquo;</a></li><li><a href=\"javascript:;\" page=\"2\"  class=\"pager-nav\">尾页</a></li></ul></div>") ;
		if(StringUtils.isNotEmpty(pageable.getSearchProperty())){
			jsonObject.put("pager","<div><ul class=\"pagination pagination-centered\"><li><a href=\"javascript:;\" page="+pageable.getSearchProperty()+"  class=\"pager-nav\">下一页&raquo;</a></ul></div>") ;
		}else{
			jsonObject.put("pager","");
		}
		ResponseUtils.renderJson(response, jsonObject.toString());
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, String[] ids) {
		if (ids != null) {
			try {
				fileService.delete(ids);
				ResponseUtils.renderJson(response,null,200,"删除成功" );
			} catch (Exception e) {
				ResponseUtils.renderJson(response,null,500,"无法删除" );
			}
		}else{
			ResponseUtils.renderJson(response,null,500,"未选择图片" );
		}

	}


}