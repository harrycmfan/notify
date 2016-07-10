package com.jxdd.ecp.notify.core;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ReflectionUtils;

import com.jxdd.ecp.notify.core.builder.DefultNotifyBeanBuilder;
import com.jxdd.ecp.notify.core.builder.NotifyBeanBuilder;
import com.jxdd.ecp.notify.core.constant.NotifyConstant;
import com.jxdd.ecp.notify.core.stereotype.NotifyComponent;
import com.jxdd.ecp.notify.core.stereotype.NotifyConsumer;
import com.jxdd.ecp.notify.core.stereotype.NotifyProducer;
import com.jxdd.ecp.notify.core.utils.AOPTargetUtils;

public class NotifyApplicationContextHolder implements ApplicationListener<ContextRefreshedEvent> {

	
	private NotifyBeanBuilder notifyBeanBuilder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		 List<NotifyMateData> consumerList = new ArrayList<NotifyMateData>();
		 List<NotifyMateData> producerList = new ArrayList<NotifyMateData>();
		// TODO Auto-generated method stub
		Map<String, Object> beansWithAnnotation = event.getApplicationContext().getBeansWithAnnotation(NotifyComponent.class);
		Set<String> beanNames = beansWithAnnotation.keySet();
		for (Iterator iterator = beanNames.iterator(); iterator.hasNext();) {
			String beanName = (String) iterator.next();
			Object o  ;
			try {
				o = AOPTargetUtils.getTarget(beansWithAnnotation.get(beanName));
				if(o != null){
					Class<? extends Object> beanClass = o.getClass();
					Method[] methods = beanClass.getDeclaredMethods();
					for (int i = 0; i < methods.length; i++) {
						Method method = methods[i];
						NotifyConsumer consumer = method.getAnnotation(NotifyConsumer.class);
						if (consumer != null) {
							
							String[] queues = consumer.queueName().split(",");
							for(String queue : queues) {
								consumerList.add(new NotifyMateData(queue,
										beanName, method.getName(), consumer.queueType(),
										NotifyConstant.NOTIFY_TYPE_CONSUMER,consumer.queueListener()));
							}
						}
						NotifyProducer notiryProducer = method.getAnnotation(NotifyProducer.class);
						if (notiryProducer != null) {
							producerList.add(new NotifyMateData(notiryProducer
									.queueName(), beanName, method.getName(),
									notiryProducer.queueType(),
									NotifyConstant.NOTIFY_TYPE_PRODUCER));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			this.getNotifyBeanBuilder().build(producerList, consumerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public NotifyBeanBuilder getNotifyBeanBuilder() {
		if (notifyBeanBuilder == null) {
			this.notifyBeanBuilder = new DefultNotifyBeanBuilder();
		}
		return notifyBeanBuilder;
	}

	public void setNotifyBeanBuilder(NotifyBeanBuilder notifyBeanBuilder) {
		this.notifyBeanBuilder = notifyBeanBuilder;
	} 
}
