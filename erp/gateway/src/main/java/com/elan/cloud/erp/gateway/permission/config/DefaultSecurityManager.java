package com.elan.cloud.erp.gateway.permission.config;

import com.elan.cloud.erp.gateway.permission.bean.Resource;
import com.elan.cloud.erp.gateway.permission.service.ResourceService;
import com.elan.cloud.erp.gateway.permission.utils.AntPathRequestMatcher;
import com.elan.cloud.erp.gateway.permission.utils.AntResourceMatcher;
import com.elan.cloud.erp.gateway.permission.utils.ResourceMatcher;
import com.elan.cloud.erp.gateway.permission.utils.ResourceURLMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

public  class DefaultSecurityManager implements SecurityManager{
    private Collection<ResourceService> resourceConfig = new ArrayList<>();
    private Collection<Resource> resourceCollection = new ArrayList<>();
    private ResourceMatcher resourceMatcher;
    private AntResourceMatcher antResourceMatcher;

    public DefaultSecurityManager(){
        resourceMatcher = new ResourceURLMatcher(false);
        antResourceMatcher = new AntPathRequestMatcher(false);
    }

    /**
     *  添加权限资源
     * @param resourceService
     */
    public void loadRecourceConfig(ResourceService resourceService){
       resourceConfig.add(resourceService);
        this.resourceCollection.addAll(resourceService.loadResourceDefine());
    }

    /**
     * 根据URL获取对应的权限要求
     * @param key   对应的URL
     *
     */
    public Collection<Resource> getResourceConfigForURL(String key) {
        Collection<Resource> resourcesForURL=new ArrayList<>();
       for(Resource resource:resourceCollection){
           if (resourceMatcher.matcher(resource,key)){
               resourcesForURL.add(resource);
           }
       }
       return resourcesForURL;
    }

    /**
     * 返回所有权限
     * @return
     */
    public  Collection<Resource> getResourceAll(){
        return this.resourceCollection;
    }

    /**
     * 获取用户权限
     */
    public  Collection<Resource> getUserResource(String id){
        Collection<Resource> userResource = new ArrayList<>();
        for(ResourceService resourceService : resourceConfig){
            userResource.addAll(resourceService.loadResourceById(id));
        }
        return userResource;
    }

    /**
     * 判断用户权限是否许可
     * @return true许可，false 不允许
     */
    public boolean decide(HttpServletRequest request,String id){
        String url=request.getRequestURI();
        Collection<Resource> resources =getResourceConfigForURL(url);
        if (resources==null || resources.size()==0) return true;
        Collection<Resource> userResources = getUserResource(id);
        if (userResources==null || userResources.size()==0) return false;
        return antResourceMatcher.matches(resources,userResources);
    }
}
