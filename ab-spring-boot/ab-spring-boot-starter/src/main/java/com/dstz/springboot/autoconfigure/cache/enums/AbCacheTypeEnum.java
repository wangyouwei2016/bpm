package com.dstz.springboot.autoconfigure.cache.enums;

import com.dstz.springboot.autoconfigure.cache.AbMemoryCacheConfiguration;
import com.dstz.springboot.autoconfigure.cache.AbRedisCacheConfiguration;
import com.dstz.springboot.autoconfigure.cache.J2CacheConfiguration;

/**
 *
 * 缓存类型
 *
 * @author wacxhs
 */
public enum AbCacheTypeEnum {

    /**
     * 内存缓存
     */
    MEMORY(AbMemoryCacheConfiguration.class),

    /**
     * REDIS 缓存
     */
    REDIS(AbRedisCacheConfiguration.class),

    /**
     * J2CACHE 缓存
     */
    J2CACHE(J2CacheConfiguration.class);

    private final Class<?> configClass;

    AbCacheTypeEnum(Class<?> configClass) {
        this.configClass = configClass;
    }

    public Class<?> getConfigClass() {
        return configClass;
    }
}
