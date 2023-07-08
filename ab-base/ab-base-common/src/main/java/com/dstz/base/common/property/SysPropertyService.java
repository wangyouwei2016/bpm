package com.dstz.base.common.property;

/**
 * @author jinxia.hou
 * @Name SysPropertyService
 * @description: 系统配置服务
 * @date 2022/2/1415:53
 */
public interface SysPropertyService {

    /**
     * 根据别名返回字符串参数值。
     *
     * @param code 参数编码
     * @return 参数值
     */
    String getValByCode(String code);


    /**
     * 根据别名返回整型参数值。
     *
     * @param code 参数编码
     * @return 参数值
     */
    Integer getIntByCode(String code);

    /**
     * 根据别名返回长整型参数值。
     *
     * @param code 参数编码
     * @return 参数值
     */
    Long getLongByCode(String code);

    /**
     * 根据别名返回布尔参数值。
     *
     * @param code 参数编码
     * @return 参数值
     */
    Boolean getBooleanByCode(String code);

}
