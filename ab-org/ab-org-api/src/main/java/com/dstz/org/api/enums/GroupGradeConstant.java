package com.dstz.org.api.enums;

import java.util.Arrays;

/**
 * 组织级别
 * 
 * @author wacxhs
 */
public enum GroupGradeConstant {

	/**
	 * 集团
	 */
	GROUP("0", "0-集团"),
	/**
	 * 公司
	 */
	COMPANY("1", "1-公司"),

	/**
	 * 部门
	 */
	DEPARTMENT("3", "3-部门"),

	/**
	 * 班组
	 */
	TEAM("5", "5-班组");


	/**
	 * 级别KEY
	 */
	private final String key;

	/**
	 * 级别标签
	 */
	private final String label;

	GroupGradeConstant(String key, String label) {
		this.key = key;
		this.label = label;
	}

	public static GroupGradeConstant valueOfKey(Integer key) {
		return key == null ? null : valueOfKey(key.toString());
	}

	public static GroupGradeConstant valueOfKey(String key) {
		return Arrays.stream(values()).filter(item -> item.getKey().equals(key)).findFirst().orElse(null);
	}

	public String getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}
}
