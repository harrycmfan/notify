package com.jxdd.ecp.notify.core.producer;

import com.jxdd.ecp.notify.core.serializable.JSONMessageSerialiazble;
import com.jxdd.ecp.notify.core.serializable.MessageSerialiazble;

public abstract class AbstractProducerObserver implements ProducerObserver {
	
	private MessageSerialiazble messageSerialiazble;
	
	public MessageSerialiazble getMessageSerialiazble() {
		if(messageSerialiazble  == null){
			messageSerialiazble = new  JSONMessageSerialiazble();
		}
		return messageSerialiazble;
	}

	public void setMessageSerialiazble(MessageSerialiazble messageSerialiazble) {
		this.messageSerialiazble = messageSerialiazble;
	}

	
}
