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

    @Value("${security.login.fail:/login}")
    private String login;

    @Value("${security.excludes:/css,/js,/lib,/images,*.ico}")
    private String excludes;


    @Bean
    public FilterRegistrationBean csrfFilterRegistrationBean() {
        logger.info("LoginFilter is running -----------------------------");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(3);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap();
        initParameters.put("excludes", excludes);
        initParameters.put("login", login);
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
