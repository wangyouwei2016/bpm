package com.dstz.org.api.model;

/**
 * 组类型接口
 *
 * @author wacxhs
 */
public interface IGroup extends java.io.Serializable {

	/**
	 * 属性-别名
	 */
	String ATTR_CODE = "code";

	/**
	 * 获取组ID
	 *
	 * @return 组ID
	 */
	String getGroupId();

	/**
	 * 获取组名称
	 *
	 * @return 组名称
	 */
	String getGroupName();

	/**
	 * 获取组类型
	 *
	 * @return 组类型
	 */
	String getGroupType();

	/**
	 * 组编码
	 *
	 * @return 组编码
	 */
	String getGroupCode();

	/**
	 * 获取上级ID
	 *
	 * @return 上级ID
	 */
	default String getParentId() {
		return null;
	}

	/**
	 * 获取组织级别
	 * @return组织级别
	 */
	Integer getGroupLevel();

	/**
	 * 获取属性值
	 *
	 * @param attrName 属性名称
	 * @param tClass   属性值类型类
	 * @param <T>      T
	 * @return 属性值
	 */
	<T> T getAttrValue(String attrName, Class<T> tClass);
}
