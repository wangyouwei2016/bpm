package com.dstz.base.query;

import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.ApiException;

public enum ConditionType {
	/**
	 * 等于
	 */
	EQUAL("EQ", "=", "等于", new String[] { "varchar", "number", "date", "clob" }),
	/**
	 * 等于忽略大小写
	 */
	/*	EQUAL_IGNORE_CASE("EIC", "=", "等于忽略大小写", new String[] { "varchar" }),*/
	/**
	 * 小于
	 */
	LESS("LT", "<", "小于", new String[] { "number", "date" }),
	/**
	 * 大于
	 */
	GREAT("GT", ">", "大于", new String[] { "number", "date" }),
	/**
	 * 小于等于
	 */
	LESS_EQUAL("LE", "<=", "小于等于", new String[] { "number", "date" }),
	/**
	 * 大于等于
	 */
	GREAT_EQUAL("GE", ">=", "大于等于", new String[] { "number", "date" }),
	/**
	 * 不等于
	 */
	NOT_EQUAL("NE", "!=", "不等于", new String[] { "varchar", "number", "date", "clob" }),
	/**
	 * 相似
	 */
	LIKE("LK", "like", "相似", new String[] { "varchar", "clob" }),
	/**
	 * 右相似
	 */
	LEFT_LIKE("LFK", "llike", "右相似", new String[] { "varchar", "clob" }),
	/**
	 * 左相似
	 */
	RIGHT_LIKE("RHK", "rlike", "左相似", new String[] { "varchar", "clob" }),
	/**
	 *
	 */
	IS_NULL("INL", "is null", "为空", new String[] { "varchar", "number", "date", "clob" }, false),
	/**
	 *
	 */
	NOTNULL("NNL", "is not null", "非空", new String[] { "varchar", "number", "date", "clob" }, false),
	/**
	 * 在...中
	 */
	IN("IN", "in", "在...中", new String[] { "varchar", "number", "date" }),
	/**
	 * 不在...中
	 */
	NOT_IN("NI", "not in", "不在...中", new String[] { "varchar", "number", "date" }),
	/**
	 * 在...之间
	 */
	BETWEEN("BT", "between", "在...之间", new String[] { "number", "date" });

	private String key;
	private String condition;
	private String desc;
	private String[] supports;
	/**
	 * 是否需要参数
	 */
	private boolean needParam = true;

	private ConditionType(String key, String condition, String desc, String[] supports) {
		this.key = key;
		this.condition = condition;
		this.desc = desc;
		this.supports = supports;
	}

	private ConditionType(String key, String condition, String desc, String[] supports, boolean needParam) {
		this(key, condition, desc, supports);
		this.needParam = needParam;
	}

	public static ConditionType formKey(String key) {
		for (ConditionType condtion : ConditionType.values()) {
			if (condtion.key.equals(key))
				return condtion;
		}

		throw new ApiException(GlobalApiCodes.PARAMETER_INVALID.formatMessage("入参条件类型不合法：{}", key));
	}

	public String key() {
		return key;
	}

	public String condition() {
		return condition;
	}

	public String desc() {
		return desc;
	}

	public String[] supports() {
		return supports;
	}

	public boolean needParam() {
		return needParam;
	}
}
