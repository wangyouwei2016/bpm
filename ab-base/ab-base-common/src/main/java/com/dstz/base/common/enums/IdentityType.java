package com.dstz.base.common.enums;

/**
 * 人员类型
 * 可以根据情况扩展更多类型，目前AB用到的 user ，role ，org ，post
 *
 */
public enum IdentityType {
	 USER("user", "用户"),
	 ROLE("role", "角色"),
	 ORG("org", "组织"),
	 GROUP("group", "小组"),
	 POST("post", "岗位");

	/**
	 * 键
	 */
	private final String key;
	/**
	 * 值
	 */
	private final String value;

	IdentityType(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return key;
	}

	/**
	 * 通过key获取对象
	 * 
	 * @param key
	 * @return
	 */
	public static IdentityType fromKey(String key) {
		for (IdentityType c : IdentityType.values()) {
			if (c.getKey().equalsIgnoreCase(key))
				return c;
		}
		throw new IllegalArgumentException(key);
	}
}
