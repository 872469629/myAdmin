/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.controller.admin;

import cn.gleme.Message;
import cn.gleme.Pageable;
import cn.gleme.entity.ImsEweiShopAdv;
import cn.gleme.service.ImsEweiShopAdvService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Controller - 幻灯片
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Controller("adminImsEweiShopAdvController")
@RequestMapping("/admin/ims_ewei_shop_adv")
public class ImsEweiShopAdvController extends BaseController {

	@Resource(name = "imsEweiShopAdvServiceImpl")
	private ImsEweiShopAdvService imsEweiShopAdvService;


	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {

		return "/v3admin/ims_ewei_shop_adv/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ImsEweiShopAdv imsEweiShopAdv, RedirectAttributes redirectAttributes) {
		if (!isValid(imsEweiShopAdv)) {
			return ERROR_VIEW;
		}

		if(imsEweiShopAdv.getId()!=null){
			imsEweiShopAdvService.update(imsEweiShopAdv);
		}else{
            imsEweiShopAdvService.save(imsEweiShopAdv);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("bean", imsEweiShopAdvService.find(id));
		return "/v3admin/ims_ewei_shop_adv/add";
	}


	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", imsEweiShopAdvService.findPage(pageable));
		return "/v3admin/ims_ewei_shop_adv/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
    Message delete(Long[] ids) {
        imsEweiShopAdvService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}