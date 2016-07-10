package com.jxdd.ecp.notify.core.serializable;

import com.jxdd.ecp.notify.core.MessageSupporter;
import com.jxdd.ecp.notify.core.serializable.util.JsonUtils;

public class JSONMessageSerialiazble implements MessageSerialiazble{

	@Override
	public Object serialiazble(MessageSupporter ms) {
		return JsonUtils.getInstance().javaObjectToString(ms);
	}

	@Override
	public MessageSupporter unSerialiazble(String ms) {
		return (MessageSupporter) JsonUtils.getInstance().jsonStringToJavaObject(ms, MessageSupporter.class);
	}

}
