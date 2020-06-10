package com.optimagrowth.gateway.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.core.annotation.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(1)
@Component
public class TrackingFilter implements GlobalFilter {

	@Autowired
	public FilterUtils util;
	
	private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class); 
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders(); 
		if (util.getCorrelationId(requestHeaders) != null) {
			logger.debug("sss-correation-Id is already present on the request header: {}",
					util.getCorrelationId(requestHeaders));
		} else {
			String correlationID = java.util.UUID.randomUUID().toString();
			exchange = util.setCorrelationId(exchange, correlationID);
			logger.debug("sss-correation-Id was generated in the TrackingFilter and set in the header: {}",
					correlationID);
		}
		return chain.filter(exchange);
	}
	
}
