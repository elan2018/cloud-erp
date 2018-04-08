package com.elan.cloud.erp.gateway.permission.bean;

/**
 * 资源接口
 * 所有资源都继承此接口
 * 例如：menu、action、file、time
 */
public interface Resource {
    public long getId();
    public String getName();
    public String getUrl();
    public boolean equals(Resource resource);
}
