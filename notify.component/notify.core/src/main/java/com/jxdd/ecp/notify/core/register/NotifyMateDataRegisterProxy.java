package com.jxdd.ecp.notify.core.register;

import java.util.List;

import com.jxdd.ecp.notify.core.NotifyMateData;

public interface NotifyMateDataRegisterProxy {

	public NotifyMateDataRegisterService getNotifyMateDataRegisterService();
	
	public void setNotifyMateDataRegisterService(
			NotifyMateDataRegisterService notifyMateDataRegisterService) ;
	

	
	public void doservice(List<NotifyMateData> producerList,
			List<NotifyMateData> consumerList) throws Exception;
}
