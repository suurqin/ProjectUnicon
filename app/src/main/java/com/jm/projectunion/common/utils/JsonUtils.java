package com.jm.projectunion.common.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Json解析工具类
 * 
 * @author Young
 * @date 2015-5-4 下午5:15:10
 */
public class JsonUtils {
	public static final Gson gson = new Gson();

	/**
	 * 将HashMap转换为Json字符串
	 * 
	 * @param data
	 *            HashMap
	 * @return json字符串
	 */
	public static String mapToJson(HashMap<String, Object> map) {
		return gson.toJson(map);
	}

	/**
	 * 将对象转换为Json字符串
	 * 
	 * @param object
	 *            对象
	 * @return Json字符串
	 * @author Young
	 * @date 2015年5月7日 上午11:16:55
	 */
	public static String ObjectToJson(Object object) {
		return gson.toJson(object);
	}

	/*
	*//**
	 * 将字符串转换成对象
	 * 
	 * @param json
	 *            字符串
	 * @param clazz
	 *            对象
	 * @return
	 */
	/*
	 * public static <T> ObjectBean<T> fromJsonToObject(String json, Class<?>
	 * clazz) throws JsonSyntaxException{ Gson gson = new Gson(); Type
	 * objectType = type(ObjectBean.class,clazz); return gson.fromJson(json,
	 * objectType); }
	 *//**
	 * 将字符串转换成集合对象
	 * 
	 * @param json字符串
	 * @param clazz
	 *            集合中的对象
	 * @return
	 */
	/*
	 * public static <T> ListBean<T> fromJsonToList(String json, Class<?> clazz)
	 * throws JsonSyntaxException{ Gson gson = new Gson(); Type objectType =
	 * type(ListBean.class,clazz); return gson.fromJson(json, objectType); }
	 */
	/**
	 * 类型
	 * 
	 * @param raw
	 * @param args
	 * @return
	 */
	public static ParameterizedType type(final Class<?> raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw;
			}

			public Type[] getActualTypeArguments() {
				return args;
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}
}
