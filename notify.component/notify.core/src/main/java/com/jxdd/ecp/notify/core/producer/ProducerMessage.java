package com.jxdd.ecp.notify.core.producer;

import java.util.Map;

import com.jxdd.ecp.notify.core.MessageDestination;


public interface ProducerMessage {

	public <T> Map<MessageDestination, T> getProduceroducerMessgeMap();
	
	public <T> void  putProducerMessge(MessageDestination destination,T producerBody);
	
	
}