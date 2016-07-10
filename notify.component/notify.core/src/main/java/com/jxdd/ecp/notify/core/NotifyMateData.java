package com.jxdd.ecp.notify.core;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.jxdd.ecp.notify.core.serializable.util.JsonUtils;
import com.jxdd.ecp.notify.core.utils.AddressUtils;

public class NotifyMateData {

	
	public NotifyMateData(String queueName, String beanName, String methodName,
			String queueType, String notifyType) {
		super();
		this.queueName = StringUtils.trimToEmpty(queueName);
		this.beanName = StringUtils.trimToEmpty(beanName) ;
		this.methodName = StringUtils.trimToEmpty(methodName);
		this.queueType = queueType;
		this.notifyType = notifyType;
		init();
	}
	
	public NotifyMateData(String queueName, String beanName, String methodName,
			String queueType, String notifyType,int queueListener) {
		super();
		this.queueName = StringUtils.trimToEmpty(queueName);
		this.beanName = StringUtils.trimToEmpty(beanName) ;
		this.methodName = StringUtils.trimToEmpty(methodName);
		this.queueType = queueType;
		this.notifyType = notifyType;
		this.queueListener = queueListener;
		init();
	}


	public void init(){
		try {
			UUID uuid = UUID.randomUUID();
			this.id= uuid.toString().replace("-", "");
			@SuppressWarnings("unchecked")
			Map<String, String> map = AddressUtils.getLocalAddress();
			if(map != null){
				this.hostName = map.get("hostName");
				this.ipaddress = map.get("hostAddress");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public NotifyMateData(){
		init();
	}
	

	private String id;
	
	private String hostName;
	
	private String ipaddress;
	
	private String queueName;
	
	private String beanName;
	
	private String methodName;
	
	private String queueType;
	
	private String notifyType;
	
	private int queueListener;
	
    public int getQueueListener() {
		return queueListener;
	}

	public void setQueueListener(int queueListener) {
		this.queueListener = queueListener;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

}
