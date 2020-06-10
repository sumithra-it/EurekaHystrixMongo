package com.optimagrowth.gateway.filters;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtils {
	public static final String CORRELATION_ID = "sss-correlation-id";
	public static final String AUTH_TOKEN     = "sss-auth-token";
	public static final String USER_ID        = "sss-user-id";
	public static final String ORG_ID         = "sss-org-id";
	public static final String PRE_FILTER_TYPE = "pre";
	public static final String POST_FILTER_TYPE = "post";
	public static final String ROUTE_FILTER_TYPE = "route";

	public String getCorrelationId(HttpHeaders header) {
		if(header.get(CORRELATION_ID) != null) {
			List<String> headerList = header.get(CORRELATION_ID);
			return headerList.stream().findFirst().get();
		} else {
		return null;
		}
	}
	
	public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
		return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
	}
	
/* Configure a consumer to modify the current request using a builder.
Effectively this:

 exchange.mutate().request(builder-> builder.method(HttpMethod.PUT));

 // vs...

 ServerHttpRequest request = exchange.getRequest().mutate()
     .method(HttpMethod.PUT)
     .build();

 exchange.mutate().request(request);
 */
	public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
		return exchange.mutate().request(
										exchange.getRequest().mutate().header(name, value).build())
								.build();
	}
}
