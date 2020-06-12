package com.optimagrowth.organization.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*Intercepts all Http requests coming into service and map correlation id from http request 
* to UserContext class
*/
@Component
public class UserContextFilter implements Filter{
	
	private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		
		UserContextHolder.getContext().setCorreleationId(httpReq.getHeader(UserContext.CORRELATION_ID));
		UserContextHolder.getContext().setAuthToken(httpReq.getHeader(UserContext.AUTH_TOKEN));
		UserContextHolder.getContext().setOrganizationId(httpReq.getHeader(UserContext.ORGANIZATION_ID));
		UserContextHolder.getContext().setUserId(httpReq.getHeader(UserContext.USER_ID));

		
		logger.debug("UserContextFilter correlationId:" + UserContextHolder.getContext().getCorreleationId());
		
		chain.doFilter(request, response);
	}

}
