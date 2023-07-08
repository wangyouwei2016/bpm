package com.dstz.base.common.enums;


import cn.hutool.core.util.ReflectUtil;

public interface IEnumEqual {
	/**
	 * <pre>
	 * 【注意这不是取值】枚举KEY 的的字段名字的定义，
	 * 用于通用接口，统一所有枚举取key对比逻辑使用
	 * 默认是"key"，也可以是"type","name"等
	 * </pre>	
	 * @return
	 */
	default String getKeyName() {
		return "key";
	}
	
	default boolean equalsWithKey(String key) {
        return key.equals(ReflectUtil.getFieldValue(this, getKeyName()));
    }
}
