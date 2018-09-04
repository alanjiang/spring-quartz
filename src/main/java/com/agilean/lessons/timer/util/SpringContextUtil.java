package com.agilean.lessons.timer.util;
import org.springframework.beans.BeansException;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.agilean.lessons.timer.util.SystemInit;

public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext; 
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException{
		SpringContextUtil.applicationContext = applicationContext;
		try
		{
		((SystemInit)getBean("init")).afterApplicationContextSet();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	public static <E> E getBean(String name, Class<E> requiredType)
			throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	
	public static boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	
	public static Class<?> getType(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	
	public static String[] getAliases(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}
}