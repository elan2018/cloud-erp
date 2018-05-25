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
import java.util.HashMap;
import java.util.Map;

/**
 * 权限安全管理
 * 1、收集系统需要保护的资源信息
 * 2、
 */
public  class DefaultSecurityManager implements SecurityManager{

    //用户权限资源缓存
    private Map<String,Collection<Resource>> cacheUserResource = null;

    //所有权限资源服务类的集合
    private Collection<ResourceService> resourceConfigList = new ArrayList<>();

    //所有需要保护的权限资源集合
    private Collection<Resource> resourceCollection = new ArrayList<>();

    //需保护的资源匹配器（与请求的资源比较）
    private ResourceMatcher resourceMatcher;

    //用户权限资源匹配器（与用户权限资源比较）
    private AntResourceMatcher antResourceMatcher;

    private void init(){
        resourceMatcher = new ResourceURLMatcher(false);//根据URL匹配资源是否需要保护
        antResourceMatcher = new AntPathRequestMatcher(false);//判断两个resouce是否匹配（用户是否有匹配的权限）
    }
    public DefaultSecurityManager(){
        init();
        cacheUserResource = new HashMap<>();
    }


    /**
     * 装入系统需要保护的权限资源配置（仅一次）
     * @param resourceService 权限资源服务
     */
    public void loadRecourceConfig(ResourceService resourceService){
        resourceConfigList.add(resourceService);
        this.resourceCollection.addAll(resourceService.loadResourceDefine());
    }

    /**
     * 根据URL获取对应的权限要求
     * 备注：存在同一URL,有不同的保护要求比如：时间、method等，目前没有实现
     * 备注描述日期: 2018-5-23
     * @param key  URL对应的权限要求列表
     *
     */
    private Collection<Resource> getResourceConfigForURL(String key) {
        Collection<Resource> resourcesForURL=new ArrayList<>();
       for(Resource resource:resourceCollection){
           if (resourceMatcher.matcher(resource,key)){
               resourcesForURL.add(resource);
           }
       }
       return resourcesForURL;
    }

    /**
     *
     * @return 返回所有需要保护的权限资源
     */
    public  Collection<Resource> getResourceAll(){
        return this.resourceCollection;
    }

    /**
     * 获取用户权限
     */
    private  Collection<Resource> getUserResource(int userId){
        Collection<Resource> userResource =this.cacheUserResource.get("userResource_"+String.valueOf(userId));
        if(userResource==null) {
            userResource = new ArrayList<>();
            for (ResourceService resourceService : resourceConfigList) {
                userResource.addAll(resourceService.loadResourceById(userId));
            }
            this.cacheUserResource.put("userResource_"+String.valueOf(userId),userResource);
        }
        return userResource;
    }

    public Collection<Resource> clearUserResourceCache(int userId){
        return this.cacheUserResource.remove("userResource_"+String.valueOf(userId));
    }
    /**
     * 判断用户权限是否许可
     * @return true许可，false 不允许
     */
    public boolean decide(HttpServletRequest request,int userId){
        String url=request.getRequestURI();
        Collection<Resource> resources =getResourceConfigForURL(url);
        if (resources==null || resources.size()==0) return true;    //资源不需要保护
        Collection<Resource> userResources = getUserResource(userId);
        if (userResources==null || userResources.size()==0) return false;
        return antResourceMatcher.matches(resources,userResources); //判断资源是否匹配
    }
}
