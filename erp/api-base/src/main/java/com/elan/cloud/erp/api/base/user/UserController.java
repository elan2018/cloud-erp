package com.elan.cloud.erp.api.base.user;

import com.elan.common.response.ResponseResult;
import com.elan.common.springboot.starter.rocketmq.common.DefaultRocketMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private DefaultRocketMqProducer defaultRocketMqProducer;
    @GetMapping("/menu")
    public Object getUserMenu(@RequestParam("userId")String userId) {
        List<Map<String,Object>> menuList = new ArrayList<>();
        for(int i=0;i<6;i++){
            Map<String,Object> subMenu =new HashMap();
            subMenu.put("name","系统管理"+i);
            subMenu.put("pic","/images/icon"+i+".png");
            List<Map<String,Object>> subMenuList = new ArrayList<>();
            for(int j=0;j<10;j++) {
                Map<String, Object> item = new HashMap<>();
                item.put("name", "菜单子功能" + i+j);
                item.put("url", "/html/index"+j);
                subMenuList.add(item);
            }
            subMenu.put("menu",subMenuList);
            menuList.add(subMenu);

        }
        ResponseResult result = new ResponseResult(menuList);
        return result;
    }
}
