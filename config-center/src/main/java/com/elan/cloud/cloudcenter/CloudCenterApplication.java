package com.elan.cloud.cloudcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CloudCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudCenterApplication.class, args);
	}
}
