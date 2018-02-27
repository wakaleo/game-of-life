package com.huilian.hlej.jet.common.utils.json;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author MingJun_Guo
 * @Mail:guomingjun1990@126.com
 * @创建日期：2014年3月1日 上午10:52:17
 * 
 * @类说明：json实体对象处理
 */
public class JsonHelper {

	/**
	 * 抽取JSON 对象中list对象
	 * 
	 * @param jsontext
	 *            json字符串
	 * @param list_str
	 *            list 对象名称
	 * @param clazz
	 *            T
	 * @return List<T>
	 */
	public static final <T> List<T> getList(String jsontext, String list_str,
			Class<T> clazz) {
		JSONObject jsonobj = JSON.parseObject(jsontext);
		if (jsonobj == null) {
			return null;
		}

		Object obj = jsonobj.get(list_str);
		if (obj == null) {
			return null;
		}

		// if(obj instanceof JSONObject){}
		if (obj instanceof JSONArray) {
			JSONArray jsonarr = (JSONArray) obj;
			List<T> list = new ArrayList<T>();
			for (int i = 0; i < jsonarr.size(); i++) {
				list.add(jsonarr.getObject(i, clazz));
			}
			return list;
		}

		return null;
	}

	/**
	 * 将JSON中的对象转为实体对象
	 * 
	 * @param jsontext
	 *            JSON字符串
	 * @param obj_str
	 *            获取实体对象名
	 * @param clazz
	 *            T
	 * @return T
	 */
	public static final <T> T getObject(String jsontext, String obj_str,
			Class<T> clazz) {
		JSONObject jsonobj = JSON.parseObject(jsontext);
		if (jsonobj == null) {
			return null;
		}

		Object obj = jsonobj.get(obj_str);
		if (obj == null) {
			return null;
		}

		if (obj instanceof JSONObject) {
			return jsonobj.getObject(obj_str, clazz);
		}

		return null;
	}

	/**
	 * 将JSON对象转为实体对象，传入任意的jsontext,返回的T都不会为null,只是T的属性为null
	 * 
	 * @param jsontext
	 * @param clazz
	 * @return
	 */
	public static final <T> T getObject(String jsontext, Class<T> clazz) {
		return JSON.parseObject(jsontext, clazz);
	}

	/**
	 * 将实体对象转为JSON字符串
	 * 
	 * @param object
	 * @param prettyFormat
	 *            是否格式化
	 * @return String
	 */
	public static final String toJSONString(Object object, boolean prettyFormat) {
		return JSON.toJSONString(object, prettyFormat);
	}

}
