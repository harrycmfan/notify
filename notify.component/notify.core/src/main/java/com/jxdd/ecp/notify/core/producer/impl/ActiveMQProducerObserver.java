package com.jxdd.ecp.notify.core.producer.impl;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.jxdd.ecp.notify.core.MessageDestination;
import com.jxdd.ecp.notify.core.MessageSupporter;
import com.jxdd.ecp.notify.core.producer.AbstractProducerObserver;
import com.jxdd.ecp.notify.exception.NotifyException;

public class ActiveMQProducerObserver extends AbstractProducerObserver {
	
	private JmsTemplate jmsTemplate;
	private Logger logger = LoggerFactory.getLogger(ActiveMQProducerObserver.class);
	@Override
	public void produceMessager(MessageSupporter ms,
			MessageDestination destination) throws NotifyException, Exception {
		
		//将传输载体序列化
		final Object jmsObject = super.getMessageSerialiazble().serialiazble(ms);
		
		jmsTemplate.setDefaultDestinationName(destination.getQueueName());
		
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				logger.info("send message......");
				return session.createObjectMessage((Serializable) jmsObject);
			}
		});
	}
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
