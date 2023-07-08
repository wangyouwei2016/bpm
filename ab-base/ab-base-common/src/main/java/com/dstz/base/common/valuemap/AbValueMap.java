package com.dstz.base.common.valuemap;

import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.valuemap.loader.AbNullValueMapLoader;

import java.lang.annotation.*;

/**
 * 值映射
 *
 * @author wacxhs
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AbValueMap {

    /**
     * 映射类型
     *
     * @return 映射类型
     */
    AbValueMapType type();

    /**
     * 加载器
     *
     * @return 加载器
     */
    Class<? extends AbValueMapLoader<?, ?>> loader() default AbNullValueMapLoader.class;

    /**
     * 固定值
     *
     * @return 固定值
     */
    String fixValue() default StrUtil.EMPTY;

    /**
     * 匹配字段
     *
     * @return 匹配字段
     */
    String matchField() default StrUtil.EMPTY;

    /**
     * 固定类
     *
     * @return 固定类
     */
    Class<?>[] fixClass() default {};

    /**
     * 字段映射，未配置则渲染加载器所有字段
     *
     * @return 字段映射
     */
    AttrMap[] attrMap() default {};

    /**
     * 属性映射
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface AttrMap {

        /**
         * 原字段名称
         *
         * @return 原字段名称
         */
        String originName();

        /**
         * 目标字段名称，允许为空
         *
         * @return 映射目标字段名称
         */
        String targetName() default StrUtil.EMPTY;
    }
}
