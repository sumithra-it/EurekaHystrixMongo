package com.optimagrowth.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import java.util.Map;
import java.util.HashMap;

/*
 * @EnableResourceServer - Convenient annotation for OAuth2 Resource Servers, enabling a Spring Security filter that authenticates requests 
 * via an incoming OAuth2 token. Users should add this annotation and provide a @Bean of type ResourceServerConfigurer (e.g. via ResourceServerConfigurerAdapter) 
 * that specifies the details of the resource (URL paths and resource id). In order to use this filter you must @EnableWebSecurity somewhere in 
 * your application, either in the same place as you use this annotation, or somewhere else.
 * 
 * @EnableAuthorizationServer - for enabling an Authorization Server (i.e. an AuthorizationEndpoint and a TokenEndpoint) in the current application context
 */
@SpringBootApplication
@RestController
@EnableResourceServer
@EnableAuthorizationServer
public class AuthenticationServiceApplication {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}
	

	@RequestMapping(value={"/user"}, produces="application/json")
	public Map<String, Object> user(OAuth2Authentication user){ //OAuth 2 authentication token can contain two authentications: one for the client and one for the user.
		Map<String, Object> userInfoMap = new HashMap<>();
		userInfoMap.put("user", user.getUserAuthentication().getPrincipal());
		userInfoMap.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
		//springframework AuthorityUtils - Utility method for manipulating GrantedAuthority collections etc.
		return userInfoMap;
	}
}
