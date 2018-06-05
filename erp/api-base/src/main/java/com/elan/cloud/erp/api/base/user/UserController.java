package com.elan.cloud.erp.api.base.user;

import com.elan.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/menu")
    public Object getUserMenu(@RequestParam("userId")String userId) {
        List<Map<String,Object>> menuList = userService.getUserMenu(userId);
        List<Map<String,Object>> mainMenu = new ArrayList<>();
        Map<String,Object> mainMenuItem =new HashMap();
        Map<String,Object> subMenuItem =new HashMap();
        int pid=0;
        boolean init=false;
        List<Map<String,Object>> subMenuList = new ArrayList<>();
        for (Map<String, Object> menu : menuList) {
            int curPid = (int)menu.get("pid");
            int id = (int)menu.get("id");
            if (curPid==0) {
                if(init) {
                    mainMenuItem.put("menu", subMenuList);
                    mainMenu.add(mainMenuItem);
                }
                init=true;
                mainMenuItem =new HashMap();
                mainMenuItem.put("url",menu.get("url"));
                mainMenuItem.put("name", menu.get("name"));
                mainMenuItem.put("pic",menu.get("pic"));
                subMenuList = new ArrayList<>();
                pid=id;
                continue;
            }
            if (curPid==pid) {
                subMenuItem =new HashMap();
                subMenuItem.put("url",menu.get("url"));
                subMenuItem.put("name", menu.get("name"));
                subMenuItem.put("pic",menu.get("pic"));
                subMenuList.add(subMenuItem);
            }

        }
        if(init) {
            mainMenuItem.put("menu", subMenuList);
            mainMenu.add(mainMenuItem);
        }
        List<String> info = new ArrayList<>();
        info.add("上次登录时间：2018-05-20 10:30:22");
        info.add("你共有"+subMenuItem.size()+"个菜单权限！");

        return new ResponseResult(mainMenu,info);
    }


}
