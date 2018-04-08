package com.elan.cloud.erp.gateway.permission.service;

import com.elan.cloud.erp.gateway.permission.bean.Resource;

import java.util.Collection;

public interface ResourceService {
   public Collection<Resource> loadResourceDefine();
   public Collection<Resource> loadResourceById(String id);

}
