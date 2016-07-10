package com.jxdd.ecp.notify.core.producer;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jxdd.ecp.notify.core.MessageDestination;
import com.jxdd.ecp.notify.core.MessageSupporter;
import com.jxdd.ecp.notify.core.stereotype.NotifyProducer;
import com.jxdd.ecp.notify.core.stereotype.ProducerBody;
import com.jxdd.ecp.notify.exception.NotifyException;

@Aspect
public class NotifyProducerServiceAdvisor implements NotifyProducerAdvice {
	
	private ProducerObserver producerObserver;
	private Logger logger = LoggerFactory.getLogger(NotifyProducerServiceAdvisor.class);
	@Around("@annotation(notifyProducer)")
	public Object around(ProceedingJoinPoint pjp, NotifyProducer notifyProducer)
			throws Throwable {
		logger.info("NotifyProducerServiceAdvisor send message");
		Object result = null;
		result = pjp.proceed(pjp.getArgs());
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		if (notifyProducer != null) {
			ProducerBody pbody = signature.getMethod().getAnnotation(
					ProducerBody.class);
			if (pbody != null) {
				produerBodyByAnnotation(pjp, notifyProducer, result, pbody);
			} else if (result instanceof ProducerMessage) {
				produerBodyByType(pjp, result);
			} else {
				producerBodyByField(pjp, result, notifyProducer);
			}
		}
		return result;
	}

	public void setProducerObserver(ProducerObserver producerObserver) {
		this.producerObserver = producerObserver;
	}
	
	private void producerBodyByField(ProceedingJoinPoint pjp, Object result, NotifyProducer notifyProducer) throws NotifyException, Exception {
		Field[] fields = FieldUtils.getAllFields(result.getClass());
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (null != field.getAnnotation(ProducerBody.class)) {
				System.out.println(field.getName());
				MessageDestination destination = new MessageDestination(
						notifyProducer.queueName(),
						notifyProducer.queueType());
				if (!StringUtils.isEmpty(field.getAnnotation(
						ProducerBody.class).queueName())) {
					destination.setQueueName(field.getAnnotation(
							ProducerBody.class).queueName());
					destination.setQueueType(field.getAnnotation(
							ProducerBody.class).queueType());
				}
				MessageSupporter ms = new MessageSupporter(pjp
						.getTarget().getClass().getName(), pjp
						.getSignature().getName(),
						FieldUtils.readField(field, result, true));
				producerObserver.produceMessager(ms, destination);
			}
		}
	}

	private void produerBodyByType(ProceedingJoinPoint pjp, Object result)
			throws NotifyException, Exception {
		ProducerMessage presult = (ProducerMessage) result;
		Map<MessageDestination, Object> produceroducerMessgeMap = presult
				.getProduceroducerMessgeMap();
		Set<MessageDestination> keySet = produceroducerMessgeMap.keySet();
		for (Iterator<MessageDestination> iterator = keySet.iterator(); iterator
				.hasNext();) {
			MessageDestination messageDestination = (MessageDestination) iterator
					.next();
			Object value = produceroducerMessgeMap.get(messageDestination);
			MessageSupporter ms = new MessageSupporter(pjp.getTarget()
					.getClass().getName(), pjp.getSignature().getName(), value);
			producerObserver.produceMessager(ms, messageDestination);
		}
	}

	private void produerBodyByAnnotation(ProceedingJoinPoint pjp,
			NotifyProducer notifyProducer, Object result, ProducerBody pbody)
			throws NotifyException, Exception {
		MessageDestination destination = new MessageDestination(
				notifyProducer.queueName(), notifyProducer.queueType());
		MessageSupporter ms = new MessageSupporter(pjp.getTarget().getClass()
				.getName(), pjp.getSignature().getName(), result);
		producerObserver.produceMessager(ms, destination);
	}
}
