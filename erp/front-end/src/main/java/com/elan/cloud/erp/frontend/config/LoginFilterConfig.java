package com.elan.cloud.erp.frontend.config;

import com.elan.cloud.erp.frontend.filter.LoginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LoginFilterConfig {
    private Logger logger = LoggerFactory.getLogger(LoginFilterConfig.class);

    //跳转到登录页面
    @Value("${security.login.fail:/login}")
    private String login;

    //排除不拦截的URL
    @Value("${security.controller.excludes:/css,/js,/lib,/images,*.ico}")
    private String excludes;

    @Bean
    public FilterRegistrationBean loginFilterRegistrationBean() {
        logger.info("LoginFilter is running -----------------------------");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap();
        initParameters.put("excludes", excludes);
        initParameters.put("login", login);
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }


}
