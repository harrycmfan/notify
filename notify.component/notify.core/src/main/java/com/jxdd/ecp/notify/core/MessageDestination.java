package com.jxdd.ecp.notify.core;

import org.apache.commons.lang3.StringUtils;

public class MessageDestination {
	
	public static String QUEUE_TYPE_P2P="p2p";
	
	public static  String QUEUE_TYPE_TOPIC="topic";
	
	private String queueName;
	
	private String queueType=QUEUE_TYPE_P2P;
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	public MessageDestination(String queueName, String queueType) {
		super();
		if(StringUtils.isNotBlank(queueName)){
			this.queueName = queueName;
		}
		
		if(StringUtils.isNotBlank(queueType)){
			this.queueType = queueType;
		}
		
	}

	public MessageDestination(String queueName) {
		super();
		if(StringUtils.isNotBlank(queueName)){
			this.queueName = queueName;
		}
	}
	
}
