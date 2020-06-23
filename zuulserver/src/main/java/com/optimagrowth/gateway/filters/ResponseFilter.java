package com.optimagrowth.gateway.filters;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.ServerCodecConfigurer;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;

/*
 * Inject the CorrelationId back to the HTTP response header, returned to the client
 */
@Configuration
public class ResponseFilter {

	
	@Bean
	public ServerCodecConfigurer serverCodecConfigurer() {
	   return ServerCodecConfigurer.create();
	}
	
	@Autowired
	FilterUtils filterUtils;
	
	final Logger logger = LoggerFactory.getLogger(ResponseFilter.class); 
	@Bean
	public GlobalFilter postGlobalFiletr() {
		return (exchange, chain) -> {
		  return chain.filter(exchange).then(Mono.fromRunnable(() -> {	
			//Mono: A Reactive Streams Publisher that completes successfully by emitting an element, or with an error.
			  
			//grab the correlationID form the HTTP request
			HttpHeaders header = exchange.getRequest().getHeaders();
			String correlationId = filterUtils.getCorrelationId(header);
			logger.debug("Adding the correlation ID to the outgoing http repsponse {}", correlationId);
			
			//Inject the correlationID into the Response
			exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);
			logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
		}));
	   };
	}
}
