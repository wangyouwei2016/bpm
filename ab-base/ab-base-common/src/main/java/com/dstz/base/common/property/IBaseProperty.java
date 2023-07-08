package com.dstz.base.common.property;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;

import java.util.Objects;

/**
 * @author jinxia.hou
 * @Name IProperty
 * @description: 系统属性接口
 * @date 2022/2/2311:31
 */
public interface IBaseProperty {


    /**
     * 获取属性key
     * @param
     * @return
     */
    String getKey();

    /**
     * 获取属性描述
     * @return
     */
    String getDesc();

    /**
     * 获取默认值
     * @return
     */
    Object getDefaultValue();

    /**
     * 获取Yaml文件配置属性值
     *
     * @param typeClass 属性值类型
     * @param <T>       属性类
     * @return 属性值
     */
    default <T> T getYamlValue(Class<T> typeClass) {
        Object value = ObjectUtil.defaultIfNull(SpringUtil.getProperty(getKey()), getDefaultValue());
        return Objects.isNull(value) ? null : Convert.convert(typeClass, value);
    }

    /**
     * 获取系统属性值
     *
     * @param typeClass 属性类型
     * @param <T>       属性类
     * @return 属性值
     */
    default <T> T getPropertyValue(Class<T> typeClass) {
        SysPropertyService sysPropertyService = SpringUtil.getBean(SysPropertyService.class);
        Object value = ObjectUtil.defaultIfNull(sysPropertyService.getValByCode(getKey()), getDefaultValue());
        return Objects.isNull(value) ? null : Convert.convert(typeClass, value);
    }
}
