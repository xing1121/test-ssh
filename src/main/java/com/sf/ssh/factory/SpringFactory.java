package com.sf.ssh.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringFactory implements BeanFactoryAware ,ApplicationContextAware{

	private static BeanFactory beanFactory;
	
	private static ApplicationContext context;
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		SpringFactory.beanFactory = beanFactory;
	}
	
	public static BeanFactory getSpringFactory(){
		return beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringFactory.context = applicationContext;
	}
	
	public static ApplicationContext getContext(){
		return context;
	}

}
