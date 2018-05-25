package com.elan.cloud.erp.gateway.permission.service.impl;

import com.elan.cloud.erp.gateway.permission.mapper.SecurityMapper;
import com.elan.cloud.erp.gateway.permission.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    SecurityMapper securityMapper;

    public User getUser(String username){
        return securityMapper.findByUsername(username);
    }

    public User login(String username,String password){
        User user = getUser(username);
        if(user==null || !user.getPassword().equalsIgnoreCase(password)){
            return null;
        }
        return user;
    }
}
