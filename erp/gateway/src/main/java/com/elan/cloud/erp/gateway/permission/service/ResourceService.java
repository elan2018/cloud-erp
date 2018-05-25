package com.elan.cloud.erp.gateway.permission.service;

import com.elan.cloud.erp.gateway.permission.bean.Resource;

import java.util.Collection;

public interface ResourceService {
   /**
    * 获取所有资源信息（需要保护的）
    * @return 所有权限资源信息
    */
   public Collection<Resource> loadResourceDefine();

   /**
    * 根据userId获取权限资源
    * @param userId 用户ID
    * @return 用户的权限资源
    */
   public Collection<Resource> loadResourceById(int userId);

}
