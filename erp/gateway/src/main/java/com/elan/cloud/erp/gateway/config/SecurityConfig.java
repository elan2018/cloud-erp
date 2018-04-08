package com.elan.cloud.erp.gateway.config;

import com.elan.cloud.erp.gateway.permission.config.DefaultSecurityManager;
import com.elan.cloud.erp.gateway.permission.service.ResourceService;
import com.elan.cloud.erp.gateway.permission.service.impl.PermissionResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

  @Bean
  public ResourceService createPermissionResourceService(){
      return new PermissionResourceService();
  }

    @Bean
    public DefaultSecurityManager getSecurityManager(){
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.loadRecourceConfig(createPermissionResourceService());
        return defaultSecurityManager;
    }
}
