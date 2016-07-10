package com.jxdd.ecp.notify.core.producer.utils;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.jxdd.ecp.notify.core.MessageDestination;
import com.jxdd.ecp.notify.core.producer.ProducerApplicationHolder;

public class ProducerMessageUtils {

	
	public static MessageDestination createMessageDestination(String queneName,String queneType){
		return new MessageDestination(queneName,queneType);
	}
	
	public static void sendMessage(final String jmsMessage, String queueName) {
		JmsTemplate jmsTemplate = (JmsTemplate) ProducerApplicationHolder.getBean("jmsTemplate");
		
		jmsTemplate.setDefaultDestinationName(queueName);
		
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage((Serializable) jmsMessage);
			}
		});
	}
 	
}
