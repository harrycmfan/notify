package com.jxdd.ecp.notify.core.register;

import java.util.List;

import com.googlecode.asyn4j.service.AsynService;
import com.jxdd.ecp.notify.core.NotifyMateData;
import com.jxdd.ecp.notify.core.schedule.NotifySchedulerService;
import com.jxdd.ecp.notify.exception.NotifyRegisterException;

public class DefaultNotifyMateDataRegisterProxy implements NotifyMateDataRegisterProxy {

	private NotifyMateDataRegisterService notifyMateDataRegisterService;

	public NotifyMateDataRegisterService getNotifyMateDataRegisterService() {
		return notifyMateDataRegisterService;
	}

	public void setNotifyMateDataRegisterService(
			NotifyMateDataRegisterService notifyMateDataRegisterService) {
		this.notifyMateDataRegisterService = notifyMateDataRegisterService;
	}

	public AsynService getAsynService() {
		return asynService;
	}

	public void setAsynService(AsynService asynService) {
		this.asynService = asynService;
	}

	private AsynService asynService;

	public void doservice(List<NotifyMateData> producerList,
			List<NotifyMateData> consumerList) throws Exception {
		if (notifyMateDataRegisterService != null && asynService != null) {
			asynService.addWork(notifyMateDataRegisterService,
					"registerNotifyMateData", new Object[] { producerList,
							consumerList });
		} else if (notifyMateDataRegisterService != null) {
			notifyMateDataRegisterService.registerNotifyMateData(producerList,
					consumerList);
		}
	}

}
