package com.dstz.base.web.api;

import java.io.Serializable;
import java.util.Map;

/**
 * web会话属性操作，用于操作当前会话用户属性
 *
 * @author wacxhs
 */
public interface WebSessionAttributesApi {

	/**
	 * 设置值
	 *
	 * @param name  属性名
	 * @param value 属性值，如果设置null则删除对应属性
	 */
	void setValue(String name, Serializable value);

	/**
	 * 设置批量值
	 *
	 * @param values 与setValue用法一样
	 */
	void setValues(Map<String, ? extends Serializable> values);

	/**
	 * 获取值
	 *
	 * @param name 属性名
	 */
	Serializable getValue(String name);
}
