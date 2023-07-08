package com.dstz.base.common.valuemap;

/**
 * 值映射类型
 *
 * @author wacxhs
 */
public enum AbValueMapType {
	/**
	 * 枚举
	 */
	ENUM,

	/**
	 * 字典
	 */
	DICT,

	/**
	 * 分类
	 */
	TYPE,

	/**
	 * 系统属性
	 */
	SYS_PROPERTIES,

	/**
	 * 用户
	 */
	USER,

	/**
	 * 组织
	 */
	ORG,

	/**
	 * 自定义
	 */
	CUSTOM,

	/**
	 * 系统数据源
	 */
	SYS_DATA_SOURCE,
	
	/**
	 * spring的bean映射
	 */
	BEAN,

	/**
	 * 流程定义
	 */
	BPM_DEFINITION,
}
