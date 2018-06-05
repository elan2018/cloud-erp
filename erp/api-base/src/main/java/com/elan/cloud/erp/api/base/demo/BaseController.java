package com.elan.cloud.erp.api.base.demo;

import com.elan.common.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BaseController {

    //接收同名参数
    @RequestMapping("/test/params")
    public ResponseResult testSameParams(@RequestParam("a") String[] a){
        Map<String,Object> r = new HashMap<>();
        r.put("a",a);
        return new ResponseResult(r);
    }

    //post请求
    @PostMapping("/test/postInfo")
    public ResponseResult postInfo(@RequestParam("id") String id){
        Map<String,Object> r = new HashMap<>();
        r.put("id",id);

        return new ResponseResult(r);
    }

    //get请求
    @GetMapping("/test/getInfo")
    public ResponseResult getInfo(@RequestParam("id") String id){
        Map<String,Object> r = new HashMap<>();
        r.put("id",id);
        return new ResponseResult(r);
    }
}
