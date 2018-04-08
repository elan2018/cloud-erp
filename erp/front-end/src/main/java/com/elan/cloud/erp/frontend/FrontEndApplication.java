package com.elan.cloud.erp.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.elan.common.controller", "com.elan.cloud.erp"}
							//	,excludeFilters={@ComponentScan.Filter(type=  FilterType.ASSIGNABLE_TYPE,value=com.elan.controller.RouterController.class)}
								)
public class FrontEndApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(FrontEndApplication.class, args);
	}
}
