package com.elan.cloud.erp.gateway.config;

import com.elan.common.filter.LogFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LogFilterConfig {
    private Logger logger = LoggerFactory.getLogger(LogFilterConfig.class);

    @Value("${log-tools.isOpen:true}")
    private String isOpen;

    //排除不拦截的URL
    @Value("${log-tools.excludes:/css,/js,/lib,/images,*.ico}")
    private String excludes;


    @Bean
    public FilterRegistrationBean logFilterRegistrationBean() {
        logger.info("LogFilterConfig is running ! -----------------------------");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap();
        initParameters.put("excludes", excludes);
        initParameters.put("isOpen", isOpen);
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
