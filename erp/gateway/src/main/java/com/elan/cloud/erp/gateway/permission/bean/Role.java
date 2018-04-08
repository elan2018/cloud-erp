package com.elan.cloud.erp.gateway.permission.bean;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Role implements Resource,Serializable {

    private long id;
    private String name;
    private String url;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Resource resource) {
        if (resource instanceof  Resource){
            if (StringUtils.isNotEmpty(resource.getName()) && resource.equals(this.name)) return true;
        }
        return false;
    }
}