package com.elan.cloud.erp.gateway.permission.service.impl;

import com.elan.cloud.erp.gateway.permission.bean.Resource;
import com.elan.cloud.erp.gateway.permission.mapper.PermissionMapper;
import com.elan.cloud.erp.gateway.permission.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 权限资源服务
 * 1、获取系统所有需要权限控制的资源
 * 2、根据user信息获取该user拥有的权限资源
 */
public class PermissionResourceService implements ResourceService{

    @Autowired
    PermissionMapper permissionMapper;


    @Override
    public Collection<Resource> loadResourceDefine() {
        return new ArrayList<>(permissionMapper.findAll());
    }

    @Override
    public Collection<Resource> loadResourceById(int id) {
        return new ArrayList<>(permissionMapper.findByUserId(id));

    }
}
