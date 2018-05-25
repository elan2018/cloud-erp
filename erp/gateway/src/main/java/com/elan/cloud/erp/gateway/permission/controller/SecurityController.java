package com.elan.cloud.erp.gateway.permission.controller;

import com.elan.cloud.erp.gateway.permission.bean.User;
import com.elan.cloud.erp.gateway.permission.config.SecurityManager;
import com.elan.cloud.erp.gateway.permission.service.impl.SecurityService;
import com.elan.cloud.erp.gateway.tools.RedisService;
import com.elan.common.response.ResponseResult;
import com.elan.common.utils.EncrypUtil;
import com.elan.common.utils.GenerationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

@RestController
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RedisService redisService;

    @Resource(name ="securityManager")
    private SecurityManager securityManager;

    @Resource(name="encryToken")
    EncrypUtil encrypUtil;

    @PostMapping("/login")
    public Object login(String username,String password) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, UnsupportedEncodingException {
        User user =securityService.login(username,password);
        if (user==null){
            return new ResponseResult(1,"用户或密码不正确！");
        }
        //存储到缓存中
        String token = String.format("%s_%s",GenerationUtil.uuid(),user.getId());
        String key = String.format("%s_%s","token",user.getId());
        redisService.set(key,token);
        return new ResponseResult(encrypUtil.encrypt(token));
    }

    @PostMapping("/user/resource/clear")
    public Object clearUserResourceFromCache(@RequestParam("userId") int userId){
        return securityManager.clearUserResourceCache(userId);
    }
}
