package com.optimagrowth.license.utils;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/*
 * Injects the correlationID into outgoing HTTP service requests executed from RestTemplate
 */
public class UserContextInterceptor implements ClientHttpRequestInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(UserContextInterceptor.class); 
	
	//intercept method is invoked before each http request by RestTemplate
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpHeaders headers = request.getHeaders();
		//add the tokens to the outgoing http call header
		headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorreleationId());
		headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
		logger.debug("The value of CorrelationId in the UserContextInterceptor is: " + UserContextHolder.getContext().getCorreleationId());
		return execution.execute(request, body);
	}

}
