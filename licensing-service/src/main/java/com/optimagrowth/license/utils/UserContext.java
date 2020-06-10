package com.optimagrowth.license.utils;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Component
public class UserContext {
	//POJO holds the HTTP Header values for an individual client request processed by microservices
	public static final String CORRELATION_ID = "sss-correlation-id";
	public static final String AUTH_TOKEN = "sss-auth-token";
	public static final String USER_ID = "sss-user-id";
	public static final String ORGANIZATION_ID = "sss-organization-id";
	
	private String correleationId = new String();
	private String authToken= new String();
	private String userId = new String();
	private String organizationId = new String();
}