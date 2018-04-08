package com.elan.cloud.erp.frontend.module.user.service;

import com.elan.common.response.ResponseResult;
import com.elan.common.utils.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${service.url:http://service-zuul:8767/}")
    private  String url;

    public ResponseResult login(String name, String password){
        Map<String,String> params =new HashMap<>();
        params.put("name",name);
        params.put("password",password);
        return new RestTemplateUtil(restTemplate).post(url+"security/login",params);
    }

    public ResponseResult getMenu(@RequestParam(value = "name") String name){
        Map<String,String> params =new HashMap<>();
        params.put("userId",name);
        return new RestTemplateUtil(restTemplate).get(url+"base/menu",params);
    }

    public ResponseResult test(String name){
        Map<String,String> params =new HashMap<>();
        params.put("name",name);
        return new RestTemplateUtil(restTemplate).get(url+"user/do",params);
    }
}
