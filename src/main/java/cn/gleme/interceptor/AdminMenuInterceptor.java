/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.gleme.entity.Admin;
import cn.gleme.entity.AuthResource;
import cn.gleme.service.AdminService;
import cn.gleme.service.AuthResourceService;

/**
 * Interceptor - 后台菜单
 * 
 * @author XJANY Team
 * @version 4.0
 */
public class AdminMenuInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "authResourceServiceImpl")
	private AuthResourceService authResourceService;

	/**
	 * 请求后处理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param handler
	 *            处理器
	 * @param modelAndView
	 *            数据视图
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Admin currentUser = adminService.getCurrent();
		if (currentUser != null) {
			List<AuthResource> myAllResourceList = (List<AuthResource>) request.getSession()
					.getAttribute("myAllResourceList" + currentUser.getId());
			if (myAllResourceList == null || myAllResourceList.size() <= 0) {
				myAllResourceList = authResourceService.allResource(currentUser);
				request.getSession().setAttribute("myAllResourceList" + currentUser.getId(), myAllResourceList);
			}
			List<AuthResource> oneResourceList = (List<AuthResource>) request.getSession()
					.getAttribute("oneResourceList");
			if (oneResourceList == null || oneResourceList.size() <= 0) {
				oneResourceList = authResourceService.getChildMenu(1l, currentUser);
				request.getSession().setAttribute("oneResourceList", oneResourceList);
			}
			String url = request.getRequestURI();
			AuthResource curentAuthResource = authResourceService.getAuthResourceByUrl(url);
			request.setAttribute("oneResourceList", oneResourceList);
			//当前权限，可能是一级权限，也可能是二级权限
			request.setAttribute("curentAuthResource", curentAuthResource);
			//一级权限和该一级权限下的所有权限
			request.setAttribute("sencondAuthResource", authResourceService.getSencondAuthResource(url));
			request.setAttribute("myAllResourceList", myAllResourceList);
		}
	}
}