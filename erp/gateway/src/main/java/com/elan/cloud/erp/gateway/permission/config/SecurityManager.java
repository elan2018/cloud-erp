package com.elan.cloud.erp.gateway.permission.config;

import com.elan.cloud.erp.gateway.permission.bean.Resource;
import com.elan.cloud.erp.gateway.permission.service.ResourceService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface SecurityManager {
    /**
     *  添加资源服务
     * @param resourceService
     */
    public void loadRecourceConfig(ResourceService resourceService);

    /**
     * 根据URL获取对应的权限要求
     * @param key   对应的URL
     *
     */
    public Collection<Resource> getResourceConfigForURL(String key);

    /**
     * 所有权限
     * @return
     */
    public  Collection<Resource> getResourceAll();

    /**
     * 获取用户权限属性
     */
    public   Collection<Resource> getUserResource(String id);

    /**
     * 判断用户权限是否许可
     * @return true许可，false 不允许
     */
    public boolean decide(HttpServletRequest request, String id);
}
