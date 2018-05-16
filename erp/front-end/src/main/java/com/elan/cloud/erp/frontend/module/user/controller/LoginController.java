package com.elan.cloud.erp.frontend.module.user.controller;

import com.elan.cloud.erp.frontend.module.user.service.LoginService;
import com.elan.common.response.ResponseResult;
import com.elan.common.utils.GenerationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    private  int index=1;
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(HttpServletRequest request,Model model,
                                            @RequestParam("name")String name,
                                            @RequestParam("password")String password){
        ResponseResult responseResult = loginService.login(name,password);
        if (responseResult.getCode()==0) {
            request.getSession().setAttribute("x-access-token", GenerationUtil.uuid());
            return "main";
        }

        model.addAttribute("error",responseResult.getMessage());
        return "login";
    }
    @RequestMapping(value={"/"})
    public String index(HttpServletRequest request){

        return "main";
    }

    @GetMapping("/user/menu")
    @ResponseBody
    public Object getUserMenu(@RequestParam("userId")String userId){

        ResponseResult reslut=  loginService.getMenu(userId);
        return reslut;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "test";
    }

}
