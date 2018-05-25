package com.elan.cloud.erp.gateway.config;

import com.elan.cloud.erp.gateway.permission.config.DefaultSecurityManager;
import com.elan.cloud.erp.gateway.permission.service.ResourceService;
import com.elan.cloud.erp.gateway.permission.service.impl.PermissionResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 权限安全管理配置
 *
 * 可以添加多个获取权限资源的服务，加入到安全管理类中
 *
 */
@Configuration
public class SecurityConfig {

    /**
     *
     * @return 从数据库中获取权限资源的服务
     */
  @Bean
  public ResourceService createPermissionResourceService(){
      return new PermissionResourceService();
  }

    /**
     *
     * @return 默认的权限安全管理类
     */
    @Bean("securityManager")
    public DefaultSecurityManager getSecurityManager(){
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.loadRecourceConfig(createPermissionResourceService());
        return defaultSecurityManager;
    }
}
