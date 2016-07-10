package com.jxdd.ecp.notify.exception;

public class NotifyRegisterException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8398920215162819758L;
	
	private Throwable throwable;
	
	public NotifyRegisterException(Throwable throwable){
		this.setThrowable(throwable);
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
