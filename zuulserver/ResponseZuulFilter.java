package com.optimagrowth.gateway.filters;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.netflix.zuul.context.RequestContext;
//import org.springframework.cloud.sleuth.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import brave.Tracer;

@Component
public class ResponseZuulFilter { //extends ZuulFilter{
	private static final int  FILTER_ORDER=1;
    private static final boolean  SHOULD_FILTER=true;
    private static final Logger logger = LoggerFactory.getLogger(ResponseZuulFilter.class);
    
//    @Autowired
//    Tracer tracer;
//    
//	@Override
//	public boolean shouldFilter() {
//		return SHOULD_FILTER;
//	}
//
//	@Override
//	public Object run() throws ZuulException {
//		RequestContext reqcontext = RequestContext.getCurrentContext();
//		reqcontext.getResponse().addHeader("sr-correlation-id", tracer.currentSpan().context().traceIdString());
//		return null; 
//	}
//
//	@Override
//	public String filterType() {
//		return "post";
//	}
//
//	@Override
//	public int filterOrder() {
//		return  FILTER_ORDER;
//	}
	
}
