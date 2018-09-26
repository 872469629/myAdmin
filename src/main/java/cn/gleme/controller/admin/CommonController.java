/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import cn.gleme.entity.Admin;
import cn.gleme.entity.AuthResource;
import cn.gleme.service.AdminService;
import cn.gleme.service.AuthResourceService;
import cn.gleme.service.CaptchaService;

/**
 * Controller - 共用
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Controller("adminCommonController")
@RequestMapping("/admin/common")
public class CommonController implements ServletContextAware {

	@Value("${system.name}")
	private String systemName;
	@Value("${system.version}")
	private String systemVersion;
	@Value("${system.description}")
	private String systemDescription;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "authResourceServiceImpl")
	private AuthResourceService authResourceService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
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

	/**
	 * 主页
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(ModelMap model) {
		Admin currentUser = adminService.getCurrent();
		List<AuthResource> secResourceList = authResourceService.getChildMenu(1l, currentUser);
		// List<AuthResource> secRootResourceList =
		// authResourceService.find(1l).getChildren();
		List<AuthResource> myAllResourceList = authResourceService.allResource(currentUser);
		model.addAttribute("secResourceList", secResourceList);
		// model.addAttribute("secRootResourceList", secRootResourceList);
		model.addAttribute("myAllResourceList", myAllResourceList);
		return "/admin/common/main";
	}

	/**
	 * 首页
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request) {
		model.addAttribute("merch", 0);
		model.addAttribute("order1", 1);
		model.addAttribute("order4", 1);
		model.addAttribute("notice", "");
		model.addAttribute("commission1", 0);
		model.addAttribute("commission2", 0);
		model.addAttribute("comment", 0);
		model.addAttribute("foldnav", 0);
		model.addAttribute("ordercol", 6);
		Cookie[] cookies = request.getCookies();
		Integer foldpanel = 1;// 1.消息提醒,2.收起面板
		for (Cookie c : cookies) {
			if ("foldpanel".equals(c.getName()) && StringUtils.isNotEmpty(c.getValue())) {
				foldpanel = Integer.parseInt(c.getValue());
			}
		}
		model.addAttribute("foldpanel", foldpanel);
		// 顶部的右边菜单
		Map<String, Object> right_menu = new HashMap<>();
		right_menu.put("system", 1);
		String menu_title = "曹尼玛哦";
		menu_title = menu_title.length() > 60 ? menu_title.subSequence(0, 60).toString() : menu_title;
		right_menu.put("menu_title", menu_title);
		List<Map<String, Object>> menu_items = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>();
		map1.put("text", "切换公众号");
		map1.put("href", "#");
		map1.put("icon", "icow-qiehuan");
		menu_items.add(map1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("text", "编辑公众号");
		map2.put("href", "#");
		map2.put("icon", "icow-bianji5");
		menu_items.add(map2);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("text", "支付方式");
		map3.put("href", "#");
		map3.put("icon", "icow-zhifu");
		menu_items.add(map3);
		Map<String, Object> map4 = new HashMap<>();
		map4.put("text", "权限管理");
		map4.put("href", "#");
		map4.put("icon", "icow-quanxian");
		menu_items.add(map4);
		Map<String, Object> map5 = new HashMap<>();
		map5.put("text", "应用授权");
		map5.put("href", "#");
		map5.put("icon", "icow-shouquan");
		menu_items.add(map5);
		Map<String, Object> map6 = new HashMap<>();
		map6.put("text", "修改密码");
		map6.put("href", "#");
		map6.put("icon", "icow-shouquan");
		menu_items.add(map6);
		right_menu.put("menu_items", menu_items);
		model.addAttribute("right_menu", right_menu);
		List<Map<String, Object>> routes = new ArrayList<>();
		model.addAttribute("routes", routes);
		model.addAttribute("role", "founder");
		// 最近访问
		List<Map<String, Object>> history = getHistory();
		model.addAttribute("history", history);
		return "/admin/index";
	}

	/**
	 * 最近访问
	 */
	public List<Map<String, Object>> getHistory() {
		List<Map<String, Object>> history = new ArrayList<>();
		Map<String, Object> h1 = new HashMap<>();
		h1.put("title", "支付设置");
		h1.put("url", "#");
		history.add(h1);
		Map<String, Object> h2 = new HashMap<>();
		h2.put("title", "幻灯片");
		h2.put("url", "#");
		history.add(h2);
		Map<String, Object> h3 = new HashMap<>();
		h3.put("title", "订单-待发货");
		h3.put("url", "#");
		history.add(h3);
		return history;
	}

	/**
	 * 获取 全部菜单带路由
	 */
	public List<Map<String, Object>> getSubMenus() {
		List<Map<String, Object>> menus = new ArrayList<>();
		Map<String, Object> m1 = new HashMap<>();
		m1.put("route", "shop");
		m1.put("text", "店铺");
		m1.put("icon", "store");
		m1.put("url", "#");
		menus.add(m1);
		Map<String, Object> m2 = new HashMap<>();
		m2.put("route", "goods");
		m2.put("text", "商品");
		m2.put("icon", "goods");
		m2.put("url", "#");
		menus.add(m2);
		Map<String, Object> m3 = new HashMap<>();
		m3.put("route", "member");
		m3.put("text", "会员");
		m3.put("icon", "member");
		m3.put("url", "#");
		menus.add(m3);
		Map<String, Object> m4 = new HashMap<>();
		m4.put("route", "order");
		m4.put("text", "订单");
		m4.put("icon", "order");
		m4.put("url", "#");
		menus.add(m4);
		Map<String, Object> m5 = new HashMap<>();
		m5.put("route", "store");
		m5.put("text", "门店");
		m5.put("icon", "mendianguanli");
		m5.put("url", "#");
		Map<String, Object> m6 = new HashMap<>();
		m6.put("route", "sale");
		m6.put("text", "营销");
		m6.put("icon", "yingxiao");
		m6.put("url", "#");
		menus.add(m6);
		Map<String, Object> m7 = new HashMap<>();
		m7.put("route", "finance");
		m7.put("text", "财务");
		m7.put("icon", "31");
		m7.put("url", "#");
		menus.add(m7);
		Map<String, Object> m8 = new HashMap<>();
		m8.put("route", "statistics");
		m8.put("text", "数据");
		m8.put("icon", "statistics");
		m8.put("url", "#");
		menus.add(m8);
		Map<String, Object> m9 = new HashMap<>();
		m9.put("route", "app");
		m9.put("text", "小程序");
		m9.put("icon", "xiaochengxu");
		m9.put("url", "#");
		menus.add(m9);
		Map<String, Object> m10 = new HashMap<>();
		m10.put("route", "plugins");
		m10.put("text", "应用");
		m10.put("icon", "plugins");
		m10.put("url", "#");
		menus.add(m10);
		Map<String, Object> m11 = new HashMap<>();
		m11.put("route", "sysset");
		m11.put("text", "设置");
		m11.put("icon", "sysset");
		m11.put("url", "#");
		menus.add(m11);
		return menus;
	}

	/**
	 * 验证码
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void captcha(String captchaId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		OutputStream outputStream = response.getOutputStream();
		BufferedImage bufferedImage = captchaService.buildImage(captchaId);
		ImageIO.write(bufferedImage, "jpg", outputStream);
		outputStream.flush();
	}

}