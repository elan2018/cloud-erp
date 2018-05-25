package com.elan.cloud.erp.gateway.permission.config;

import com.elan.cloud.erp.gateway.permission.bean.Resource;
import com.elan.cloud.erp.gateway.permission.service.ResourceService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface SecurityManager {
    /**
     * 装入系统需要保护的权限资源（仅一次）
     * @param resourceService 权限资源服务
     */
    public void loadRecourceConfig(ResourceService resourceService);

//    /**
//     * 根据URL获取对应的权限要求
//     * @param key   对应的URL
//     *
//     */
//    Collection<Resource> getResourceConfigForURL(String key);
//
    /**
     *
     * @return 所有系统所需保护的权限资源
     */
    public Collection<Resource> getResourceAll();

    /**
     * 清除缓存中的用户权限资源
     */
     public Collection<Resource> clearUserResourceCache(int userId);

    /**
     * 判断用户权限是否许可
     * @return true许可，false 不允许
     */
    public boolean decide(HttpServletRequest request, int id);
}
