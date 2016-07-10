package com.jxdd.ecp.notify.core;

public class MessageSupporter {


	private String producerClassName;
	
	private String producerMethodName;
	
	private Object producerBody;
	
	
	public String getProducerClassName() {
		return producerClassName;
	}

	public void setProducerClassName(String producerClassName) {
		this.producerClassName = producerClassName;
	}

	public String getProducerMethodName() {
		return producerMethodName;
	}

	public void setProducerMethodName(String producerMethodName) {
		this.producerMethodName = producerMethodName;
	}

	public Object getProducerBody() {
		return producerBody;
	}

	public void setProducerBody(Object producerBody) {
		this.producerBody = producerBody;
	}

	public MessageSupporter(String producerClassName,
			String producerMethodName, Object producerBody) {
		super();
		this.producerClassName = producerClassName;
		this.producerMethodName = producerMethodName;
		this.producerBody = producerBody;
		
	}
	public MessageSupporter(){
		super();
	}
}
