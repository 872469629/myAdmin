/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.controller.admin;

import cn.gleme.Message;
import cn.gleme.Pageable;
import cn.gleme.entity.${model};
import cn.gleme.service.${model}Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Controller - ${name}
 * 
 * @author XJANY Team
 * @version 4.0
 */
@Controller("admin${model}Controller")
@RequestMapping("/admin/${repository}")
public class ${model}Controller extends BaseController {

	@Resource(name = "${lowerModelName}ServiceImpl")
	private ${model}Service ${lowerModelName}Service;

<#list parentSelects as column>
	@Autowired
	private ${column.name?cap_first}Service ${column.name}Service;
</#list>

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		<#list columnList as column>
			<#if "enumSelect"==column.type>
		model.addAttribute("${column.name}s",${model}.${column.name?cap_first}.values());
			</#if>
		</#list>
		<#list parentSelects as column>
		model.addAttribute("${column.name}s", ${column.name}Service.findAll());
		</#list>

		return "/v3admin/${repository}/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(${model} ${lowerModelName}, RedirectAttributes redirectAttributes) {
		if (!isValid(${lowerModelName})) {
			return ERROR_VIEW;
		}

		if(${lowerModelName}.getId()!=null){
			${lowerModelName}Service.update(${lowerModelName});
		}else{
            ${lowerModelName}Service.save(${lowerModelName});
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
<#list columnList as column>
	<#if "enumSelect"==column.type>
		model.addAttribute("${column.name}s",${model}.${column.name?cap_first}.values());
	</#if>
</#list>
<#list parentSelects as column>
		model.addAttribute("${column.name}s", ${column.name}Service.findAll());
</#list>
		model.addAttribute("bean", ${lowerModelName}Service.find(id));
		return "/v3admin/${repository}/add";
	}


	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", ${lowerModelName}Service.findPage(pageable));
		return "/v3admin/${repository}/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
    Message delete(Long[] ids) {
        ${lowerModelName}Service.delete(ids);
		return SUCCESS_MESSAGE;
	}

}