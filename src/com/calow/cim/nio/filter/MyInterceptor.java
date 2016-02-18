package com.calow.cim.nio.filter;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class MyInterceptor implements Interceptor {

	/**
	 * 防止未登录拦截器
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		System.out.println("-----------------interceptor destroy method----------------------");
	}

	@Override
	public void init() {
		System.out.println("----------------interceptor init method-------------------------");
	}

	@Override
	public String intercept(ActionInvocation invoker) throws Exception {
		System.out.println("-------------------interceptor intercept method--------------------------");
		String result=invoker.invoke();
		return result;
	}

}
