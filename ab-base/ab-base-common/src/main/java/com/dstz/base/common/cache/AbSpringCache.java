package com.dstz.base.common.cache;

import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * <pre>
 * 系统缓存注解，支持Spring缓存注解，内部支持多种缓存切换，如redis，二级缓存j2cache 
 * 作者:wacxhs
 * 邮箱:wacxhs@agilebpm.cn
 * 日期:2021-11-13
 * 版权: 深圳市大世同舟信息科技有限公司
 * </pre>
 */
public class AbSpringCache extends AbstractValueAdaptingCache {

    private final ICache cache;

    private final String region;

    public AbSpringCache(ICache cache, String region) {
        super(true);
        this.cache = cache;
        this.region = region;
    }

    private String serialString(Object key) {
        return (String) key;
    }

    @Override
    protected Object lookup(Object key) {
        return cache.getIfPresent(region, serialString(key));
    }

    @Override
    public String getName() {
        return region;
    }

    @Override
    public Object getNativeCache() {
        return cache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return (T) cache.get(region, serialString(key), (Callable<Object>) valueLoader);
    }

    @Override
    public void put(Object key, Object value) {
        cache.put(region, serialString(key), value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Object storeValue = toStoreValue(value);
        if (!Objects.isNull(lookup(key))) {
            put(key, storeValue);
        }
        return toValueWrapper(storeValue);
    }

    @Override
    public void evict(Object key) {
        cache.invalidate(region, serialString(key));
    }

    @Override
    public void clear() {
        cache.invalidateRegion(region);
    }
}
