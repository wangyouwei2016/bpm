package com.dstz.base.common.script;

/**
 * <pre>
 * Script的类型枚举
 * </pre>
 * @author aschs
 * @date 2022年6月23日
 * @owner 深圳市大世同舟信息科技有限公司
 */
public enum ScriptType {
	CONFIG("config", "配置模式"),
	HAND("hand", "手写模式");
	/**
	 * key
	 */
	private String key;
	/**
	 * 描述
	 */
	private String desc;

	private ScriptType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * <pre>
	 * 根据key来判断是否跟当前一致
	 * </pre>
	 * 
	 * @param key
	 * @return
	 */
	public boolean equalsWithKey(String key) {
		return this.key.equals(key);
	}

	/**
	 * <pre>
	 * 根据key获取
	 * </pre>
	 * 
	 * @param key
	 * @return
	 */
	public static ScriptType getByKey(String key) {
		for (ScriptType type : ScriptType.values()) {
			if (key.equals(type.getKey())) {
				return type;
			}
		}
		return null;
	}
}
