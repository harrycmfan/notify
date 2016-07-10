package com.jxdd.ecp.notify.core.consumer.proxy;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.jxdd.ecp.notify.core.NotifyMateData;
import com.jxdd.ecp.notify.exception.NotifyException;

public interface ConsumerRegisterProxy {
	
	public List<DefaultMessageListenerContainer> registerListener(List<NotifyMateData> consumerMediaList)throws NotifyException,Exception;
	
	public DefaultMessageListenerContainer registerListener(NotifyMateData consumerMedia) throws NotifyException,Exception;
	
}
