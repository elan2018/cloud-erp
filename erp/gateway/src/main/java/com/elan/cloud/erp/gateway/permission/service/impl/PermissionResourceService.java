package com.elan.cloud.erp.gateway.permission.service.impl;

import com.elan.cloud.erp.gateway.permission.bean.Resource;
import com.elan.cloud.erp.gateway.permission.mapper.PermissionMapper;
import com.elan.cloud.erp.gateway.permission.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

public class PermissionResourceService implements ResourceService{

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public Collection<Resource> loadResourceDefine() {
        Collection<Resource> resources = new ArrayList<>();
        resources.addAll(permissionMapper.findAll());
        return resources;
    }

    @Override
    public Collection<Resource> loadResourceById(String id) {
        Collection<Resource> resources = new ArrayList<>();
        resources.addAll(permissionMapper.findByUserId(id));
        return resources;
    }
}
