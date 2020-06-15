package com.optimagrowth.license;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.cloud.stream.annotation.StreamListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.optimagrowth.license.utils.UserContextInterceptor;


@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class LicenseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicenseServiceApplication.class, args);
	}

//	@StreamListener(Sink.INPUT)
//	public void loggerSink(OrganizationChangeModel orgChange) {
//		log.info("Received an event from kafka topic for orgId:{}", orgChange.getOrganizationId());
//	}
//	
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setBasenames("messages");
		return messageSource;
	}
/*
 * LoadBalanced indicates this bean is using Ribbon for client side load balancing
 */
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate(){
		RestTemplate template =  new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
		
		//add the UsrContextInterceptor to the RestTemplate instance that we created
		if (interceptors == null) {
			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		} else {
			interceptors.add(new UserContextInterceptor());
			template.setInterceptors(interceptors);
		}
		
		
		return template;
	}
	
	
}
