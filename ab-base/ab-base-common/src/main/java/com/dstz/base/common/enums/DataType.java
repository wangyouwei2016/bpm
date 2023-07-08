package com.dstz.base.common.enums;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.exceptions.BusinessException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Date;

public enum DataType {
	/**
	 * 浮点型
	 */
	DOUBLE("number","数字", Double.class),

	/**
	 * 字符串
	 */
	STRING("varchar","字符串", String.class),

	/**
	 * 布尔
	 */
	BOOL("bool","布尔", Boolean.class),

	/**
	 * 日期
	 */
	DATE("date","日期", Date.class);


	/**
	 * 类型
	 */
	private final String type;

	/**
	 * 描述
	 */
    private final String desc;

	/**
	 * java type
	 */
	@JsonIgnore
	private final Class<?> javaType;

    DataType(String type, String desc, Class<?> javaType) {
        this.type = type;
        this.desc = desc;
	    this.javaType = javaType;
    }
    

	public String getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public static Object parseValueByType(String dataType, String value) {
		if (StrUtil.isBlank(value)) {
			return null;
		}
		return Arrays.stream(values()).filter(o -> o.getType().equals(dataType))
		             .findFirst()
		             .map(o -> Convert.convert(o.getJavaType(), value))
		             .orElseThrow(() -> new BusinessException(GlobalApiCodes.PARAMETER_INVALID.formatDefaultMessage(value)));
	}

}
