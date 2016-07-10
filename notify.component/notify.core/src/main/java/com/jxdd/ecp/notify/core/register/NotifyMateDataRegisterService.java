package com.jxdd.ecp.notify.core.register;

import java.util.List;

import com.jxdd.ecp.notify.core.NotifyMateData;
import com.jxdd.ecp.notify.exception.NotifyRegisterException;

/** 
   * <strong>Title:NotifyMateDataRegisterService </strong>
   * <strong>Discription: </strong>
   * <strong>Create time: 2015年12月4日 下午3:24:08  </strong>
   * <p> www.jinxiudadi.com </p>
   *
   * <p> @author chenxin</p>
   * <p> @version: </p>
   * <p>  </p>
   * 
  **/
public interface NotifyMateDataRegisterService {

	public void registerNotifyMateData(List<NotifyMateData> producerList,
			List<NotifyMateData> consumerList) throws Exception;
	
	public void init()throws Exception;
	
	public void destroy() throws Exception;
		
}
