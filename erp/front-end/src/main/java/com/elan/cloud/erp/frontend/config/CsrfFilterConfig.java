package com.elan.cloud.erp.frontend.config;

import com.elan.common.filter.CsrfFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CsrfFilterConfig {
    private Logger logger = LoggerFactory.getLogger(CsrfFilterConfig.class);

    @Value("${security.csrf.test-token:1111-2222-3333-4444-5555}")
    private String test_token;

    @Value("${security.csrf.isOpen:true}")
    private String isOpen;

    //排除不拦截的URL
    @Value("${security.csrf.excludes:/css,/js,/lib,/images,*.ico}")
    private String excludes;


    @Bean
    public FilterRegistrationBean csrfFilterRegistrationBean() {
        logger.info("CsrfFilter is running -----------------------------");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CsrfFilter());
        filterRegistrationBean.setOrder(3);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap();
        initParameters.put("excludes", excludes);
        initParameters.put("isOpen", isOpen);
        initParameters.put("test-token",test_token);
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
