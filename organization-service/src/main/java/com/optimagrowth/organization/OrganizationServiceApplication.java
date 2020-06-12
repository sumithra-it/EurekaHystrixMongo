package com.optimagrowth.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import javax.servlet.Filter;
import com.optimagrowth.organization.utils.UserContextFilter;

@SpringBootApplication
@RefreshScope
public class OrganizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationServiceApplication.class, args);
	}
	
//	@Bean
//	public Filter userContextFilter() {
//		UserContextFilter userContextFilter = new UserContextFilter();
//		return userContextFilter;
//	}

}
