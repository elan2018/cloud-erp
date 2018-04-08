package com.elan.cloud.erp.gateway.permission.utils;

import com.elan.cloud.erp.gateway.permission.bean.Resource;

public interface ResourceMatcher {
    public boolean matcher(Resource resource, String key);
}
