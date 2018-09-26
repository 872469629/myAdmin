/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.controller.admin;

import java.util.HashSet;

import javax.annotation.Resource;

import cn.gleme.Message;
import cn.gleme.Pageable;
import cn.gleme.entity.Admin;
import cn.gleme.entity.BaseEntity;
import cn.gleme.entity.Role;
import cn.gleme.service.*;
import cn.gleme.service.AdminService;
import cn.gleme.service.RoleService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 管理员
 *
 * @author XJANY Team
 * @version 4.0
 */
@Controller("adminAdminController")
@RequestMapping("/admin/admin")
public class AdminController extends BaseController {

    @Resource(name = "adminServiceImpl")
    private AdminService adminService;
    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    /**
     * 检查用户名是否存在
     */
    @RequestMapping(value = "/check_username", method = RequestMethod.GET)
    public @ResponseBody
    boolean checkUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        return !adminService.usernameExists(username);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap model) {
        model.addAttribute("roles", roleService.findAll());
        return "/admin/admin/add";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Admin admin, Long[] roleIds, RedirectAttributes redirectAttributes,Long agentId) {
        admin.setRoles(new HashSet<Role>(roleService.findList(roleIds)));
        if (!isValid(admin, BaseEntity.Save.class)) {
            return ERROR_VIEW;
        }
        if (adminService.usernameExists(admin.getUsername())) {
            return ERROR_VIEW;
        }
        admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
        admin.setIsLocked(false);
        admin.setLoginFailureCount(0);
        admin.setLockedDate(null);
        admin.setLoginDate(null);
        admin.setLoginIp(null);
        admin.setLockKey(null);
        adminService.save(admin);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Long id, ModelMap model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("admin", adminService.find(id));
        return "/admin/admin/edit";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Admin admin, Long[] roleIds, RedirectAttributes redirectAttributes,Long agentId) {
        admin.setRoles(new HashSet<Role>(roleService.findList(roleIds)));
        if (!isValid(admin)) {
            return ERROR_VIEW;
        }
        Admin pAdmin = adminService.find(admin.getId());
        if (pAdmin == null) {
            return ERROR_VIEW;
        }
        if (StringUtils.isNotEmpty(admin.getPassword())) {
            admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
        } else {
            admin.setPassword(pAdmin.getPassword());
        }
        if (pAdmin.getIsLocked() && !admin.getIsLocked()) {
            admin.setLoginFailureCount(0);
            admin.setLockedDate(null);
        } else {
            admin.setIsLocked(pAdmin.getIsLocked());
            admin.setLoginFailureCount(pAdmin.getLoginFailureCount());
            admin.setLockedDate(pAdmin.getLockedDate());
        }
        adminService.update(admin, "username", "loginDate", "loginIp", "lockKey");
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Pageable pageable, ModelMap model) {
        model.addAttribute("page", adminService.findPage(pageable));
        return "/admin/admin/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        if (ids.length >= adminService.count()) {
            return Message.error("admin.common.deleteAllNotAllowed");
        }
        adminService.delete(ids);
        return SUCCESS_MESSAGE;
    }

}