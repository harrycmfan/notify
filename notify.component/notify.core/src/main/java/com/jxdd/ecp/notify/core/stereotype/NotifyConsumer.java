package com.jxdd.ecp.notify.core.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotifyConsumer {

	String queueName();
	
	String queueType() default "p2p";
	
	int queueListener() default 1;
}
