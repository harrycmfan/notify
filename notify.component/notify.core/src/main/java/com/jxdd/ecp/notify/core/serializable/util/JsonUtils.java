package com.jxdd.ecp.notify.core.serializable.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	private static JsonUtils uniqueInstance = new JsonUtils();
	
	public static final String TAG = "JSONUtil";

	private JsonUtils() {
	}

	public static JsonUtils getInstance() {
		return uniqueInstance;
	}
	/**
	 * java对象转换为json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public String javaObjectToString(Object obj) {
		return JSON.toJSONString(obj, true);
	}

	/**
	 * java对象转换为json字符串 不格式化
	 * 
	 * @param obj
	 * @return
	 */
	public String javaObjectToStringNoFormat(Object obj) {
		return JSON.toJSONString(obj, false);
	}

	/**
	 * java对象集合转换为json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public String listToString(Object obj) {
		return JSON.toJSONString(obj, true);
	}

	/**
	 * json字符串转换为java对象
	 * 
	 * @param str
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> Object jsonStringToJavaObject(String str, Class<T> clazz) {
		if (str.indexOf("[") != -1) {
			str = str.replace("[", "");
		}
		if (str.indexOf("]") != -1) {
			str = str.replace("]", "");
		}
		return (JSON.parseObject(str, clazz));

	}

	/**
	 * 封装将json对象转换为java集合对象
	 * 
	 * @param <T>
	 * @param clazz
	 * @param jsons
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> List<T> getJavaCollection(Class<T> clazz, String jsons) {
		List<T> objs = null;
		objs = (JSON.parseArray(jsons, clazz));
		return objs;
	}
	
	public  boolean isJSON(String value) {
		try {
			JSON.parse(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public  JSON parseJSON(String value) {
		JSON jo = null;
		try {
			jo = (JSON) JSON.parse(value);
		} catch (Exception e) {
		}
		return jo;
	}

	public  boolean isJSONObject(String value) {
		try {
			JSON.parseObject(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public  JSONObject parseObject(String value) {
		JSONObject jo = null;
		try {
			
			jo = JSON.parseObject(value);
		} catch (Exception e) {
		}
		
		return jo;
	}

	public  boolean isJSONArray(String value) {
		try {
			JSON.parseArray(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public  JSONArray parseArray(String value) {
		JSONArray ja = null;
		try {
			ja = JSON.parseArray(value);
		} catch (Exception e) {
		}
		return ja;
	}

	public  boolean isEmpty(JSONObject jo) {
		if (jo == null || jo.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public  boolean isEmpty(JSONArray ja) {
		if (ja == null || ja.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public  String getString(JSONObject params, String key) {
		return getString(params, key, "");
	}

	private  String getString(JSONObject params, String key, String df) {
		if (df == null) {
			df = "";
		}
		return getValue(params, key, df);
	}

	public  boolean getBoolean(JSONObject params, String key, boolean df) {
		return getValue(params, key, df);
	}

	public  int getInt(JSONObject params, String key) {
		return getInt(params, key, 0);
	}

	public  int getInt(JSONObject params, String key, int df) {
		return getValue(params, key, df);
	}

	public  JSONObject getJSONObject(JSONObject params, String key) {
		return getJSONObject(params, key, null);
	}

	public  JSONObject getJSONObject(JSONObject params, String key,
			JSONObject df) {
		if (df == null) {
			df = new JSONObject();
		}
		return getValue(params, key, df);
	}

	@SuppressWarnings("unchecked")
	public  <T> T getValue(JSONObject params, String key, T df) {
		if (params == null || params.isEmpty()) {
			return df;
		}
		if (df == null) {
			return df;
		}

		if (!params.containsKey(key)) {
			return df;
		}

		T value = df;
		Object obj = params.get(key);
		if (obj != null && value.getClass().isAssignableFrom(obj.getClass())) {
			value = (T) obj;
		} else {
			logger.info(TAG, "[key] " + key + " [value] " + obj);
		}
		return value;
	}
}
