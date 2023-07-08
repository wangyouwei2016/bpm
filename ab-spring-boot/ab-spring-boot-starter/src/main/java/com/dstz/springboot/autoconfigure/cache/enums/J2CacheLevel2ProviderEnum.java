package com.dstz.springboot.autoconfigure.cache.enums;


import com.dstz.component.j2cache.redis.SpringRedisCacheProvider;

/**
 * j2cache 一级缓存提供类
 *
 * @author wacxhs
 */
public enum J2CacheLevel2ProviderEnum {

    /**
     * 无 缓存
     */
    NONE("none"),

    /**
     * REDIS
     */
    REDIS(SpringRedisCacheProvider.class.getName());

    private final String provider;

    J2CacheLevel2ProviderEnum(String providerClass) {
        this.provider = providerClass;
    }

    public String getProvider() {
        return provider;
    }
}
