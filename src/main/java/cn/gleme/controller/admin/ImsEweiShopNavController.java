/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.controller.admin;

import cn.gleme.Message;
import cn.gleme.Pageable;
import cn.gleme.entity.ImsEweiShopNav;
import cn.gleme.service.ImsEweiShopNavService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Controller - 导航图标
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Controller("adminImsEweiShopNavController")
@RequestMapping("/admin/ims_ewei_shop_nav")
public class ImsEweiShopNavController extends BaseController {

	@Resource(name = "imsEweiShopNavServiceImpl")
	private ImsEweiShopNavService imsEweiShopNavService;


	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {

		return "/v3admin/ims_ewei_shop_nav/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ImsEweiShopNav imsEweiShopNav, RedirectAttributes redirectAttributes) {
		if (!isValid(imsEweiShopNav)) {
			return ERROR_VIEW;
		}

		if(imsEweiShopNav.getId()!=null){
			imsEweiShopNavService.update(imsEweiShopNav);
		}else{
            imsEweiShopNavService.save(imsEweiShopNav);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("bean", imsEweiShopNavService.find(id));
		return "/v3admin/ims_ewei_shop_nav/add";
	}


	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", imsEweiShopNavService.findPage(pageable));
		return "/v3admin/ims_ewei_shop_nav/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
    Message delete(Long[] ids) {
        imsEweiShopNavService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}