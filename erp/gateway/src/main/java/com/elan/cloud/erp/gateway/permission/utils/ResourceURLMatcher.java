package com.elan.cloud.erp.gateway.permission.utils;

import com.elan.cloud.erp.gateway.permission.bean.Resource;

import org.apache.commons.lang3.StringUtils;

public class ResourceURLMatcher implements ResourceMatcher {

    private  boolean isCase = false;

    public ResourceURLMatcher(){}
    public ResourceURLMatcher(boolean isCase){
        this.isCase = isCase;
    }
    @Override
    public boolean matcher(Resource resource,String key) {
        if (StringUtils.isNotEmpty(resource.getUrl()) ){
            String url = resource.getUrl();
            if (isCase==false){
                key =key.toLowerCase();
                url = url.toLowerCase();
            }
             if (url.equals(key)) return true;
        }
        return false;
    }
}
