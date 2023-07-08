package com.dstz.org.api.model;

/**
 * 用户接口
 *
 * @author wacxhs
 */
public interface IUser extends java.io.Serializable {

    /**
     * 属性-邮箱
     */
    String ATTR_EMAIL = "email";

    /**
     * 属性-手机号码
     */
    String ATTR_MOBILE = "mobile";

    /**
     * 过期
     */
    String ATTR_EXPIRE_DATE = "expireDate";

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    String getUserId();

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取用户姓名
     *
     * @return 用户姓名
     */
    String getFullName();

    /**
     * 获取属性值
     *
     * @param attrName 属性名称
     * @param tClass   属性类型
     * @param <T>      T
     * @return 属性值
     */
    <T> T getAttrValue(String attrName, Class<T> tClass);
}
