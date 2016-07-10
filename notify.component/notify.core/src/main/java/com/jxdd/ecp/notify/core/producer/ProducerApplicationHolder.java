package com.jxdd.ecp.notify.core.producer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ProducerApplicationHolder implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		applicationContext = ac;
	}
	
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

}
