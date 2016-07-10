package com.jxdd.ecp.notify.core.producer;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import com.jxdd.ecp.notify.core.stereotype.NotifyProducer;
import com.jxdd.ecp.notify.core.stereotype.ProducerBody;

public interface NotifyProducerAdvice {

	 public Object around(ProceedingJoinPoint pjp,NotifyProducer notifyProducer) throws Throwable;
}
