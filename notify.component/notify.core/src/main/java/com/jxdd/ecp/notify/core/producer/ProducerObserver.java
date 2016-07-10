package com.jxdd.ecp.notify.core.producer;

import com.jxdd.ecp.notify.core.MessageDestination;
import com.jxdd.ecp.notify.core.MessageSupporter;
import com.jxdd.ecp.notify.exception.NotifyException;

public interface ProducerObserver {
	  
	public void produceMessager(MessageSupporter ms,MessageDestination destination)throws NotifyException ,Exception ;
	
}
