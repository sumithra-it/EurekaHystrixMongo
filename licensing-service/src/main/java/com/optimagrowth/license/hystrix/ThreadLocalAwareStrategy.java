package com.optimagrowth.license.hystrix;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.optimagrowth.license.utils.UserContextHolder;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy{

	private HystrixConcurrencyStrategy existingStrategy;
	
	public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy existingStrategy) {
		this.existingStrategy = existingStrategy;
	}
	
	@Override
	public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize,
			HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		return (existingStrategy != null) ? existingStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize,
				keepAliveTime, unit, workQueue) : super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
			HystrixThreadPoolProperties threadPoolProperties) {
		return (existingStrategy != null) ? existingStrategy.getThreadPool(threadPoolKey, threadPoolProperties) : super.getThreadPool(threadPoolKey, threadPoolProperties);
	}

	@Override
	public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
		return  (existingStrategy != null) ? existingStrategy.getBlockingQueue(maxQueueSize) : 
														super.getBlockingQueue(maxQueueSize);
	}

	//customized for UserContext to be passed to the HystrixConcurrencyStrategy
	@Override
	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		return (existingStrategy != null) ? existingStrategy.wrapCallable(new DelegatingUserContextCallable<T>(
																callable, UserContextHolder.getContext())) 
											: super.wrapCallable(new DelegatingUserContextCallable<T>(
													callable, UserContextHolder.getContext()));
	}

	@Override
	public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
		return ( existingStrategy != null) ? existingStrategy.getRequestVariable(rv) : super.getRequestVariable(rv);
	}

}
