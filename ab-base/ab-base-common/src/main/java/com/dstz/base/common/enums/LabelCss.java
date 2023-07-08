package com.dstz.base.common.enums;

public enum LabelCss {
	/**
	 * 白色
	 */
	DEFAULT("default","默认"),
	/**
	 * 蓝色
	 */
	PRIMARY("primary","主色调"),
	/**
	 * 绿色
	 */
	SUCCESS("success","成功"),
	/**
	 * 黄色
	 */
	WARNING("warning","警告"),
	/**
	 * 红色
	 */
	ERROR("error","错误"),
	;
	
	/**
	 * 键
	 */
	private final String key;

	/**
	 * 值
	 */
	private final String value;

	LabelCss(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
