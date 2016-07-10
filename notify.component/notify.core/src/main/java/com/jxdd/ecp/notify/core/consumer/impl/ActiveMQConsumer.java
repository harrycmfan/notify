package com.jxdd.ecp.notify.core.consumer.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.beanutils.MethodUtils;
import org.springframework.jms.core.JmsTemplate;

/**
 * JMS消费者
 * 
 * 
 * <p>
 * 消息题的内容定义
 * <p>
 * 消息对象 接收消息对象后： 接收到的消息体* <p> 
 */
public class ActiveMQConsumer implements MessageListener{
	
	private Object obj;
	
	private String methodName;

	/**
	 * 监听到消息目的有消息后自动调用onMessage(Message message)方法
	 */
	@Override
	public void onMessage(Message message) {
		
		if(message instanceof ObjectMessage) {
			ObjectMessage om = (ObjectMessage) message;
			
			try {
				MethodUtils.invokeExactMethod(obj, methodName, om.getObject());
			} catch (NoSuchMethodException | IllegalAccessException
					| InvocationTargetException | JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
			try {
				MethodUtils.invokeExactMethod(obj, methodName, tm.getText());
			} catch (NoSuchMethodException | IllegalAccessException
					| InvocationTargetException | JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public void setObj(Object obj) {
		this.obj = obj;
	}

}