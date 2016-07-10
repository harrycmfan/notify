package com.jxdd.ecp.notify.core.producer;

import java.util.Map;
import java.util.TreeMap;

import com.jxdd.ecp.notify.core.MessageDestination;

public abstract class AbstractProducerMessage implements ProducerMessage {

	private TreeMap<MessageDestination ,Object> messageMap = new TreeMap<MessageDestination, Object>();
	
	@Override
	public  Map<MessageDestination, Object> getProduceroducerMessgeMap() {
		return messageMap;
	}
	
	@Override
	public <T> void putProducerMessge(MessageDestination destination,
			T producerBody) {
		messageMap.put(destination, producerBody);
	}

}
