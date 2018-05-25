package com.elan.cloud.erp.frontend.config;

import com.elan.common.filter.ReSubmitFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ResubmitFilterConfig {
    private Logger logger = LoggerFactory.getLogger(ResubmitFilterConfig.class);

    @Value("${security.resubmit.isOpen:true}")
    private String isOpen;

    @Value("${security.resubmit.test-token:000000000}")
    private String test_token;

    //排除不拦截的URL
    @Value("${security.resubmit.excludes:/css,/js,/lib,/images,*.ico}")
    private String excludes;

    @Bean
    public FilterRegistrationBean resubmitFilterRegistrationBean() {
        logger.info("ResubmitFilter is running -----------------------------");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new ReSubmitFilter());
        filterRegistrationBean.setOrder(4);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap();
        initParameters.put("excludes", excludes);
        initParameters.put("isOpen", isOpen);
        initParameters.put("test-token", test_token);
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
