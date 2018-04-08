package com.elan.cloud.erp.gateway.permission.utils;

import com.elan.cloud.erp.gateway.permission.bean.Resource;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class AntPathRequestMatcher implements AntResourceMatcher{
    private String subpath=null;
    private int length=0;
    private String method=null;
    private boolean isCase = false;

    public AntPathRequestMatcher(boolean isCase){
        this.isCase = isCase;
    }
    public AntPathRequestMatcher(String method,String subpath,boolean isCase){
        this.method = method;
        this.subpath=subpath;
        this.length = subpath.length();
        if (isCase==false){
            this.subpath = this.subpath.toLowerCase();
        }
    }
    public AntPathRequestMatcher(String subpath,boolean isCase){
        this.subpath=subpath;
        this.length = subpath.length();
        if (isCase==false){
            this.subpath = this.subpath.toLowerCase();
        }
    }

    public AntPathRequestMatcher(String subpath){
        this.length = subpath.length();
        this.subpath = this.subpath.toLowerCase();

    }


    public boolean matches(HttpServletRequest request){
        String path=request.getRequestURI();
        if (StringUtils.isNotEmpty(this.method)){
            return this.method.equalsIgnoreCase(request.getMethod()) ||  (path.startsWith(this.subpath) && (path.length() == this.length || path.charAt(this.length) == '/'));
        }
        return (path.startsWith(this.subpath) && (path.length() == this.length || path.charAt(this.length) == '/'));
    }


    public boolean matches(String path){
        return (path.startsWith(this.subpath) && (path.length() == this.length || path.charAt(this.length) == '/'));
    }

    @Override
    public boolean matches(Collection<Resource> source, Collection<Resource> target) {
        for(Resource resource :source){
            if (StringUtils.isNotEmpty(resource.getUrl())) {
                for (Resource user : target) {
                    if (StringUtils.isNotEmpty(user.getUrl())) {
                        String s_url = resource.getUrl();
                        String t_url = user.getUrl();
                        if (this.isCase==false){
                            s_url = s_url.toLowerCase();
                            t_url = t_url.toLowerCase();
                        }
                        if (s_url.equals(t_url)) return true;
                    }
                }
            }
        }
        return false;
    }
}
