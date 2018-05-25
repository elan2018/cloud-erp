package com.elan.cloud.erp.api.base.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<Map<String,Object>> getUserMenu(int userId){
        return userMapper.getUserMenu(userId);
    }
}
