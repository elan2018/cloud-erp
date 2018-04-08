package com.elan.cloud.erp.frontend.demo;

import com.elan.common.response.ResponseResult;
import com.elan.common.utils.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${service.url:http://service-zuul:8767/}")
    private  String url;

        @GetMapping("/test/error")
        public ResponseResult testError() {
            Map<String,String> params =new HashMap<>();
            params.put("name","2");
            ResponseResult responseResult = new RestTemplateUtil(restTemplate).get(url +"base/err",params);
            return responseResult;
        }

        @GetMapping("/test/ok")
        public ResponseResult hello(HttpSession httpSession) {
            Map<String,String> params =new HashMap<>();
            params.put("userId","1");
            params.put("password","123");
            ResponseResult responseResult = new RestTemplateUtil(restTemplate).get(url +"base/menu",params);
            return responseResult;
        }

}
