package com.elan.cloud.erp.gateway.permission.utils;

import com.elan.cloud.erp.gateway.permission.bean.Resource;

import java.util.Collection;

public interface AntResourceMatcher {

    public boolean matches(Collection<Resource> source, Collection<Resource> target);
}
