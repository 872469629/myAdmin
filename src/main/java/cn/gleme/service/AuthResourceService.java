
/*
 * Copyright 2005-2015 gleme.cn. All rights reserved.
 * Support: http://www.gleme.cn

 */
package cn.gleme.service;

import cn.gleme.entity.Admin;
import cn.gleme.entity.AuthResource;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Set;

/**
 * Service - 资源
 *
 * @author XJANY Team
 * @version 4.0
 */
public interface AuthResourceService extends BaseService<AuthResource, Long> {
    /**
     *
     * 加载过滤配置信息
     *
     * @return
     */

    public String loadFilterChainDefinitions();

    /**
     *
     * 重新构建权限过滤器
     *
     * 一般在修改了用户角色、用户等信息时，需要再次调用该方法
     */

    public void reCreateFilterChains();

    public List<AuthResource> getByParentId(Long id, Set<AuthResource> authResources);

    public JSONObject searialTcatgory(AuthResource authResource, Set<AuthResource> authResources, List<AuthResource> myResource);

    public List<AuthResource> getChildMenu(Long nodeId,Admin admin);

    public List<AuthResource> allResource(Admin admin);

    public AuthResource getSencondAuthResource(String url);
    public AuthResource getAuthResourceByUrl(String url);
}