package com.jxdd.ecp.notify.core.builder;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.jxdd.ecp.notify.core.NotifyMateData;
import com.jxdd.ecp.notify.core.consumer.proxy.ConsumerRegisterProxy;
import com.jxdd.ecp.notify.core.producer.NotifyProducerAdvice;
import com.jxdd.ecp.notify.exception.NotifyException;

public interface NotifyBeanBuilder {

	public void build(List<NotifyMateData> producerList,List<NotifyMateData>  consumerList) throws NotifyException,Exception;
	
	public NotifyProducerAdvice  getNotifyProducerAdvice();
	
	public void  setNotifyProducerAdvice(NotifyProducerAdvice notifyProducerAdvice);
	
	public ConsumerRegisterProxy getConsumerRegisterProxy();
	 
	public void setConsumerRegisterProxy(ConsumerRegisterProxy consumerRegisterProxy) ;
	
	public List<DefaultMessageListenerContainer>  getRegisterListenerList();
}
