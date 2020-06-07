package com.optimagrowth.license.hystrix;

import java.util.concurrent.Callable;

import com.optimagrowth.license.utils.UserContext;
import com.optimagrowth.license.utils.UserContextHolder;

public class DelegatingUserContextCallable<V> implements Callable<V> {

	private final Callable<V> delegate;
	private UserContext originalUserContext;
	
	DelegatingUserContextCallable(Callable<V> delegate, UserContext originalUserContext){
		this.delegate = delegate;
		this.originalUserContext = originalUserContext;
	}
	
	@Override
	public V call() throws Exception {
		//ThreadLocal varaible that stores UserContext is associated with the thread running the Hystrix protected method
		UserContextHolder.setContext(originalUserContext);
		
		try {
			return delegate.call();
		} finally {
			this.originalUserContext = null;
		}
	}
	
	public static <V> Callable<V> create(Callable<V> delegate, UserContext originalUserContext) {
		return new DelegatingUserContextCallable<V>(delegate, originalUserContext);
	}

}
