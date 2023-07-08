package com.dstz.springboot.autoconfigure.cache.enums;

import com.dstz.component.j2cache.serialization.JacksonJsonSerializer;

/**
 * J2Cache 序列化枚举
 *
 * @author wacxhs
 */
public enum J2CacheSerializationEnum {

    /**
     * JAVA 内置序列化
     */
    JAVA("java"),

    /**
     * JSON 序列化
     */
    JSON(JacksonJsonSerializer.class.getName()),

    /**
     * FST 序列化
     */
    FST("fst");


    private final String serializer;

    J2CacheSerializationEnum(String serializer) {
        this.serializer = serializer;
    }

    public String getSerializer() {
        return serializer;
    }
}
