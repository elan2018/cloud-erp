package com.elan.cloud.erp.api.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.elan.common.controller", "com.elan.cloud.erp"}
								,excludeFilters={@ComponentScan.Filter(type=  FilterType.ASSIGNABLE_TYPE,value=com.elan.common.controller.RouterController.class)}
								)
public class BaseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseApiApplication.class, args);
	}
}
