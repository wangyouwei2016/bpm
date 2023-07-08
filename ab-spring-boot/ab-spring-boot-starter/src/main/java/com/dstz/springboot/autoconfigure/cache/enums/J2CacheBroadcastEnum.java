package com.dstz.springboot.autoconfigure.cache.enums;

import com.dstz.component.j2cache.redis.RedisPubSubClusterPolicy;

/**
 * j2cache 广播
 *
 * @author wacxhs
 */
public enum J2CacheBroadcastEnum {

    /**
     * redis 发布与订阅
     */
    REDIS(RedisPubSubClusterPolicy.class.getName()),

    /**
     * 空通知
     */
    NONE("none"),

    /**
     * 组播
     */
    JGROUPS("jgroups");

    private final String provider;

    J2CacheBroadcastEnum(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
}
