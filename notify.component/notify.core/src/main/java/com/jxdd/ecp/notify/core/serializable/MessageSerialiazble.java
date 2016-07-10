package com.jxdd.ecp.notify.core.serializable;

import com.jxdd.ecp.notify.core.MessageSupporter;

public interface MessageSerialiazble {


	public Object serialiazble(MessageSupporter ms);
	
	public MessageSupporter unSerialiazble(String ms);
	
	
}
