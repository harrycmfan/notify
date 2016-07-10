package com.jxdd.ecp.notify.core.producer.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.jxdd.ecp.notify.core.MessageDestination;
import com.jxdd.ecp.notify.core.MessageSupporter;
import com.jxdd.ecp.notify.core.producer.AbstractProducerObserver;
import com.jxdd.ecp.notify.exception.NotifyException;

public class DefaultProducerObserver extends AbstractProducerObserver {
	
	private JmsTemplate jmsTemplate;
	
	@Override
	public void produceMessager(MessageSupporter ms, MessageDestination md) throws NotifyException, Exception {
		
		//将传输载体序列化
		final Object jmsObject = super.getMessageSerialiazble().serialiazble(ms);
		
		jmsTemplate.setDefaultDestinationName(md.getQueueName());
		
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage((Serializable) jmsObject);
			}
		});
		
	}
	
	public String hello(String json) {
		  
		System.out.println(json);
		
		return json;
	}
	
	public static void main(String[] args) throws NotifyException, Exception {
		DefaultProducerObserver po = new DefaultProducerObserver();
		MessageSupporter ms = new MessageSupporter();
		ms.setProducerClassName("A");
		ms.setProducerMethodName("hahaha");
		ms.setProducerBody(new NotifyException());
		po.produceMessager(ms,null);
				
	}
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	

}
