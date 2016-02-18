package com.calow.ichat.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @描述：动态获取bean实例
 * @author calow
 *
 */
public class ContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
	
	public static Object getBean(String name){
		return context.getBean(name);
	}
	
	public static <T> T getBean(Class<T> c){
		return context.getBean(c);
	}

}
