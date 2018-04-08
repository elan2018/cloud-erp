package com.elan.cloud.erp.gateway.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @PostMapping("/login")
    public String login(){
        return "ok";
    }
}
