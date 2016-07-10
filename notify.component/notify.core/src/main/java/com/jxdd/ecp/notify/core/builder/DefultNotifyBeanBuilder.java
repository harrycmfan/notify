package com.jxdd.ecp.notify.core.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.jxdd.ecp.notify.core.NotifyMateData;
import com.jxdd.ecp.notify.core.consumer.proxy.ConsumerRegisterProxy;
import com.jxdd.ecp.notify.core.producer.NotifyProducerAdvice;
import com.jxdd.ecp.notify.core.producer.NotifyProducerServiceAdvisor;
import com.jxdd.ecp.notify.core.register.NotifyMateDataRegisterProxy;
import com.jxdd.ecp.notify.exception.NotifyException;

public class DefultNotifyBeanBuilder implements NotifyBeanBuilder,DisposableBean{

    private NotifyProducerAdvice notifyProducerAdvice;
    
	private ConsumerRegisterProxy consumerRegisterProxy;
	
 	private  List<DefaultMessageListenerContainer> registerListenerList= new LinkedList<DefaultMessageListenerContainer>();
	
 	List<NotifyMateData> producerList = new ArrayList<NotifyMateData>();
 	
 	List<NotifyMateData> consumerList = new ArrayList<NotifyMateData>();

 	
 	public NotifyMateDataRegisterProxy getNotifyMateDataRegisterProxy() {
		return notifyMateDataRegisterProxy;
	}

	public void setNotifyMateDataRegisterProxy(
			NotifyMateDataRegisterProxy notifyMateDataRegisterProxy) {
		this.notifyMateDataRegisterProxy = notifyMateDataRegisterProxy;
	}


	private NotifyMateDataRegisterProxy notifyMateDataRegisterProxy;
 	
 	
	@Override
	public void build(List<NotifyMateData> producerList,
			List<NotifyMateData> consumerList) throws NotifyException,Exception {
		for(NotifyMateData pmd : producerList) {
			this.producerList.add(pmd);
		}
		for(NotifyMateData cmd : consumerList) {
			this.consumerList.add(cmd);
			DefaultMessageListenerContainer dmlc = getConsumerRegisterProxy().registerListener(cmd);
			registerListenerList.add(dmlc);
		}
		if(notifyMateDataRegisterProxy != null){
			notifyMateDataRegisterProxy.doservice(producerList, consumerList);
		}
		
	}
	

	@Override
	public NotifyProducerAdvice getNotifyProducerAdvice() {
	    if(this.notifyProducerAdvice == null){
	    	this .notifyProducerAdvice = new NotifyProducerServiceAdvisor();
	    }
		return this .notifyProducerAdvice;
	}

	@Override
	public void setNotifyProducerAdvice(
			NotifyProducerAdvice notifyProducerAdvice) {
		this.notifyProducerAdvice = notifyProducerAdvice;
	}

    public ConsumerRegisterProxy getConsumerRegisterProxy() {
		return consumerRegisterProxy;
	}

	public void setConsumerRegisterProxy(ConsumerRegisterProxy consumerRegisterProxy) {
		this.consumerRegisterProxy = consumerRegisterProxy;
	}

	@Override
	public List<DefaultMessageListenerContainer> getRegisterListenerList() {
		return registerListenerList;
	}


	@Override
	public void destroy() throws Exception {
		for (Iterator<DefaultMessageListenerContainer> iterator = registerListenerList.iterator(); iterator
				.hasNext();) {
			DefaultMessageListenerContainer defaultMessageListenerContainer = (DefaultMessageListenerContainer) iterator.next();
			if(defaultMessageListenerContainer != null){
				defaultMessageListenerContainer.stop();
				defaultMessageListenerContainer.destroy();
			}
		}
	}



	




}
