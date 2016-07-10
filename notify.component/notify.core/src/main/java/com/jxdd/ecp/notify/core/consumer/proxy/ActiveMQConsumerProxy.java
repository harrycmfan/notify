package com.jxdd.ecp.notify.core.consumer.proxy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.jxdd.ecp.notify.core.NotifyMateData;
import com.jxdd.ecp.notify.core.consumer.impl.ActiveMQConsumer;
import com.jxdd.ecp.notify.exception.NotifyException;

public class ActiveMQConsumerProxy implements ConsumerRegisterProxy ,ApplicationContextAware {


	private PooledConnectionFactory connectionFactory ;
	
	private ApplicationContext applicationContext;

	public PooledConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	
	public void setConnectionFactory(PooledConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public List<DefaultMessageListenerContainer> registerListener(
			List<NotifyMateData> consumerMediaList) throws NotifyException,
			Exception {
		List<DefaultMessageListenerContainer> registerList= new LinkedList<DefaultMessageListenerContainer>();
		for (Iterator<NotifyMateData> iterator = consumerMediaList.iterator(); iterator.hasNext();) {
			 NotifyMateData notifyMateData = (NotifyMateData) iterator.next();
				DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
				listener.setConnectionFactory(connectionFactory);
				ActiveMQConsumer consumer = new ActiveMQConsumer();
				final Object obj = applicationContext.getBean(notifyMateData.getBeanName());
				consumer.setObj(obj);
				consumer.setMethodName(notifyMateData.getMethodName());
				listener.setupMessageListener(consumer);
				listener.setDestinationName(notifyMateData.getQueueName());
				listener.setConcurrentConsumers(notifyMateData.getQueueListener());
				listener.initialize();
				listener.start();
				registerList.add(listener);
		}
		return registerList;
	}
	
	@Override
	public DefaultMessageListenerContainer registerListener(NotifyMateData consumerMedia) throws NotifyException, Exception {
		DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
		listener.setConnectionFactory(connectionFactory);
		ActiveMQConsumer consumer = new ActiveMQConsumer();
		final Object obj = applicationContext.getBean(consumerMedia.getBeanName());
		consumer.setObj(obj);
		consumer.setMethodName(consumerMedia.getMethodName());
		listener.setupMessageListener(consumer);
		listener.setDestinationName(consumerMedia.getQueueName());
		listener.initialize();
		listener.start();
		return listener;
	}

}
