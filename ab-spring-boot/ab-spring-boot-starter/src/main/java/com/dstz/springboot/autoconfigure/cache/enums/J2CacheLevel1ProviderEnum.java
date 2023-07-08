package com.dstz.springboot.autoconfigure.cache.enums;


/**
 * j2cache 一级缓存提供类
 *
 * @author wacxhs
 */
public enum J2CacheLevel1ProviderEnum {

    /**
     * caffeine 缓存
     */
    CAFFEINE("caffeine"),

    /**
     * 无 缓存
     */
    NONE("none"),

    /**
     * EhCache 2.x 缓存
     */
    EHCACHE("ehcache"),

    /**
     * EhCache 3.x 缓存
     */
    EHCACHE3("ehcache3");

    private final String provider;

    J2CacheLevel1ProviderEnum(String providerClass) {
        this.provider = providerClass;
    }

    public String getProvider() {
        return provider;
    }
}
