package com.jxdd.ecp.notify.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.jxdd.ecp.notify.core.builder.DefultNotifyBeanBuilder;
import com.jxdd.ecp.notify.core.builder.NotifyBeanBuilder;
import com.jxdd.ecp.notify.core.constant.NotifyConstant;
import com.jxdd.ecp.notify.core.stereotype.NotifyComponent;
import com.jxdd.ecp.notify.core.stereotype.NotifyConsumer;
import com.jxdd.ecp.notify.core.stereotype.NotifyProducer;
import com.jxdd.ecp.notify.core.stereotype.ProducerBody;

@Deprecated
public class NotifyBeanFactoryHolder implements
		ApplicationListener<ContextRefreshedEvent> {

	private List<NotifyMateData> consumerList = new ArrayList<NotifyMateData>(0);

	private List<NotifyMateData> producerList = new ArrayList<NotifyMateData>(0);

	private NotifyBeanBuilder notifyBeanBuilder;

	public NotifyBeanBuilder getNotifyBeanBuilder() {
		if (notifyBeanBuilder == null) {
			this.notifyBeanBuilder = new DefultNotifyBeanBuilder();
		}
		return notifyBeanBuilder;
	}

	public void setNotifyBeanBuilder(NotifyBeanBuilder notifyBeanBuilder) {
		this.notifyBeanBuilder = notifyBeanBuilder;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Map<String, Object> beansWithAnnotation = event.getApplicationContext()
				.getBeansWithAnnotation(NotifyComponent.class);
		Set<String> beanNames = beansWithAnnotation.keySet();
		for (Iterator iterator = beanNames.iterator(); iterator.hasNext();) {
			String beanName = (String) iterator.next();
			Class<? extends Object> beanClass = beansWithAnnotation.get(
					beanName).getClass();
			Method[] methods = beanClass.getDeclaredMethods();

			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				NotifyConsumer consumer = method
						.getAnnotation(NotifyConsumer.class);
				if (consumer != null) {
					consumerList.add(new NotifyMateData(consumer.queueName(),
							beanName, method.getName(), consumer.queueType(),
							NotifyConstant.NOTIFY_TYPE_CONSUMER));
				}

				NotifyProducer notiryProducer = method
						.getAnnotation(NotifyProducer.class);
				if (notiryProducer != null) {
					producerList.add(new NotifyMateData(notiryProducer
							.queueName(), beanName, method.getName(),
							notiryProducer.queueType(),
							NotifyConstant.NOTIFY_TYPE_PRODUCER));
				}

			}
		}
		try {
			this.getNotifyBeanBuilder().build(producerList, consumerList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
