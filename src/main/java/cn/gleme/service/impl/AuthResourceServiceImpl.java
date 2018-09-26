/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.service.impl;

import cn.gleme.dao.AuthResourceDao;
import cn.gleme.entity.Admin;
import cn.gleme.entity.AuthResource;
import cn.gleme.entity.Role;
import cn.gleme.service.AuthResourceService;

import cn.gleme.util.OrderedProperties;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Service - 资源
 *
 * @author XJANY Team
 * @version 4.0
 */
@Service("authResourceServiceImpl")
public class AuthResourceServiceImpl extends BaseServiceImpl<AuthResource, Long> implements AuthResourceService {
    private static final Logger log = Logger.getLogger(AuthServiceImpl.class);

    // 注意/r/n前不能有空格

    private static final String CRLF = "\r\n";

    private static final String LAST_AUTH_STR = "/** =authc\r\n";

    @Resource
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Resource(name = "authResourceDaoImpl")
    AuthResourceDao authResourceDao;


    @Override
    public String loadFilterChainDefinitions() {

        StringBuffer sb = new StringBuffer("");
        sb.append(getFixedAuthRule())
//		.append(getDynaAuthRule())
                .append(getRestfulOperationAuthRule())
                .append(LAST_AUTH_STR);
        return sb.toString();
    }

    // 生成restful风格功能权限规则
    private String getRestfulOperationAuthRule() {

        //获取所有Resource
        List<AuthResource> list = this.findAll();

        StringBuffer sb  = new StringBuffer("");
        for (Iterator<AuthResource> it = list.iterator(); it.hasNext();) {
            AuthResource resource = it.next();
            //如果不为空值添加到section中
            if(StringUtils.isNotEmpty(resource.getActionStr())) {
//       			sb.append(resource.getActionStr()).append("=").append("authc, rest[").append(resource.getResStr()).append("]").append(CRLF);
                sb.append(resource.getActionStr()).append("=").append("perms[\"").append(resource.getActionStr()).append("\"]").append(CRLF);
            }

        }
        return sb.toString();
    }

    // 根据角色，得到动态权限规则
//	private String getDynaAuthRule() {
//       StringBuffer sb = new StringBuffer("");
//       Map<String, Set<String>> rules = new HashMap<String,Set<String>>();
//       List<Role> roles = dao.queryEntitys("from Role r left join fetch r.menus", newObject[]{});
//       for(Role role: roles) {
//           for(Iterator<Menu> menus =role.getMenus().iterator(); menus.hasNext();) {
//              String url = menus.next().getUrl();
//              if(!url.startsWith("/")) {
//                  url = "/"+ url;
//              }
//              if(!rules.containsKey(url)) {
//                  rules.put(url, newHashSet<String>());
//              }
//              rules.get(url).add((role.getRoleCode()));
//           }
//       }
//
//       for(Map.Entry<String, Set<String>> entry :rules.entrySet()) {
//           sb.append(entry.getKey()).append(" = ").append("authc,roleOrFilter").append(entry.getValue()).append(CRLF);
//       }
//       returnsb.toString();
//    }


    // 得到固定权限验证规则串
    private String getFixedAuthRule() {
        StringBuffer sb = new StringBuffer("");
        ClassPathResource cp = new ClassPathResource("fixed_auth_res.properties");
        Properties properties = new OrderedProperties();
        try {
            properties.load(cp.getInputStream());
        } catch (IOException e) {
            log.error("loadfixed_auth_res.properties error!", e);
            throw new RuntimeException("load fixed_auth_res.properties error!");
        }
        for (Iterator its = properties.keySet().iterator(); its.hasNext();) {
            String key = (String) its.next();
            sb.append(key).append(" = ")
                    .append(properties.getProperty(key).trim()).append(CRLF);
        }
        return sb.toString();
    }

    @Override
    // 此方法加同步锁
    public synchronized void reCreateFilterChains() {
        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                    .getObject();
        } catch (Exception e) {
            log.error("getShiroFilter from shiroFilterFactoryBean error!", e);
            throw new RuntimeException(
                    "get ShiroFilter from shiroFilterFactoryBean error!");
        }
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                .getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                .getFilterChainManager();
        // 清空老的权限控制
        manager.getFilterChains().clear();
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        shiroFilterFactoryBean
                .setFilterChainDefinitions(loadFilterChainDefinitions());
        // 重新构建生成
        Map<String, String> chains = shiroFilterFactoryBean
                .getFilterChainDefinitionMap();
        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue().trim().replace(" ", "");
            manager.createChain(url, chainDefinition);
        }

    }

    public List<AuthResource> getByParentId(Long id,Set<AuthResource> authResources){
//		String hql = "from AuthResource where 1=1 ";
//		if(id==null){
//			hql+=" and parent.id is null";
//		}else{
//			hql+=" and parent.id = "+id;
//		}
//		hql+=" order by sequences asc ";
//		return this.find(hql, null);
        List<AuthResource> resources = new ArrayList<>();
        if(authResources==null){
            String hql = "from AuthResource where 1=1 ";
            if(id==null){
                hql+=" and parent.id is null";
            }else{
                hql+=" and parent.id = "+id;
            }
            hql+=" order by sequences asc ";
            resources = this.find(hql, null);
        }else{
            for (AuthResource authResource : authResources) {
                if(authResource.getId()==id){
                    resources = authResource.getChildren();
                    break;
                }
            }
        }
        return resources;
    }

    public JSONObject searialTcatgory(AuthResource authResource, Set<AuthResource> authResources, List<AuthResource> myResource) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("checked", false);
        if(myResource!=null){
            for(AuthResource resource:myResource){
                if(resource.equals(authResource)){
                    jsonObject.put("checked", true);
                    break;
                }
            }
        }

        jsonObject.put("text", authResource.getResName());
        jsonObject.put("item_id", authResource.getId());
        jsonObject.put("nid", "menu_tree_"+authResource.getSequences());
        List list = searialChild(authResource,myResource,authResources);
        jsonObject.put("children", list);
        return jsonObject;
    }

    private List searialChild(AuthResource authResource,List<AuthResource> myResource,Set<AuthResource> authResources) {
        List children = null;
        List<AuthResource> list = this.getByParentId(authResource.getId(),authResources);
        if (list != null && list.size() > 0) {
            children = new ArrayList();
        }
        for (AuthResource object : list) {
            if(authResources==null||authResources.contains(object)){
                JSONObject jsonObject = searialTcatgory(object,authResources,myResource);
                children.add(jsonObject);
            }
        }
        return children;
    }

    public List<AuthResource> getChildMenu(Long nodeId,Admin admin){

        //获取子菜单
        AuthResource rootResource = this.find(nodeId);
        List<AuthResource> ret = new ArrayList<AuthResource>();
        if(rootResource!=null){
            List<AuthResource> menuResources = rootResource.getChildren();


            List<AuthResource> allResource = allResource(admin);
            for (int i=0;i<menuResources.size();i++){
                AuthResource menuResource = menuResources.get(i);
                /*根据当前登录用户的授权，判断是否添加该一级菜单子节点*/
                for(AuthResource AuthResource:allResource){
                    if(AuthResource.getId().equals(menuResource.getId()))
                        ret.add(menuResource);
                }
            }
        }
        return ret;
    }

    public List<AuthResource> allResource(Admin admin){
        Set<Role> roles = admin.getRoles();
        List<AuthResource> allResource = new ArrayList<>();
        if (roles != null) {
            for (Role role : roles) {
                allResource.addAll(role.getAuthResource());
            }
        }

        for (int i = 0; i < allResource.size() - 1; i++) {
            for (int j = allResource.size() - 1; j > i; j--) {
                if (allResource.get(i).getId().equals(allResource.get(j).getId())) {
                    allResource.remove(j);
                }
            }
        }
        return allResource;
    }


    /**
     * 通过URL获取二级菜单
     * @param url
     * @return
     */
    public AuthResource getAuthResourceByUrl(String url){
        List<AuthResource> authResources = this.find("from AuthResource where actionStr = ?",url);
        AuthResource authResource = null;
        if(authResources!=null&&authResources.size()==1){
            authResource = authResources.get(0);
        }else if(authResources!=null&&authResources.size()>=2){
            authResource = authResources.get(1);
        }
        return authResource;
    }

    /**
     * 通过URL获取二级菜单
     * @param url
     * @return
     */
    public AuthResource getSencondAuthResource(String url){
        url = url.substring(0,url.lastIndexOf("/")+1);
        List<AuthResource> authResources = this.find("from AuthResource where actionStr like ?","%"+url+"%");
        AuthResource authResource = null;
        if(authResources!=null&&authResources.size()==1){
            authResource = authResources.get(0);
            return getSecAuthResource(authResource);
        }else if(authResources!=null&&authResources.size()>=2){
            authResource = authResources.get(1);
            return getSecAuthResource(authResource);
        }else{
            return null;
        }

    }

    private AuthResource getSecAuthResource(AuthResource authResource){
        if(authResource.getParent().getId()==1){
            return authResource;
        }else if(authResource.getParent()!=null){
            return getSecAuthResource(authResource.getParent());
        }else{
            return null;
        }
    }
}