package com.optimagrowth.license.utils;

import org.springframework.util.Assert;

public class UserContextHolder {
	private static final ThreadLocal<UserContext> userContextLocal = new ThreadLocal<UserContext>();
	
	public static final UserContext getContext() {
		UserContext context = userContextLocal.get();
		if (context == null) {
			context = new UserContext();
			userContextLocal.set(context);
		}
		return userContextLocal.get();
	}
	
	public static void setContext(UserContext context) {
		Assert.notNull(context, "Only non-null UserContext instances are permitted");
		userContextLocal.set(context);
	}
}
