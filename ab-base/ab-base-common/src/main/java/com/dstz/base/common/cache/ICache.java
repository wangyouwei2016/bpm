package com.dstz.base.common.cache;

import java.util.concurrent.Callable;
/**
 * <pre>
 * 系统缓存
 * 作者:wacxhs
 * 邮箱:wacxhs@agilebpm.cn
 * 日期:2021-12-10
 * 版权: 深圳市大世同舟信息科技有限公司
 * </pre>
 */
public interface ICache {

    /**
     * 获取缓存，如果不存在返回null
     *
     * @param region 缓存区域
     * @param key    缓存键
     * @return 缓存值
     */
    <V> V getIfPresent(String region, String key);

    /**
     * 获取缓存，如果缓存不存在从loader中加载
     *
     * @param region 缓存区域
     * @param key    缓存键
     * @param loader 缓存加载器
     * @return 缓存值
     */
    <V> V get(String region, String key, Callable<V> loader);

    /**
     * 放置缓存
     *
     * @param region 缓存区域
     * @param key    缓存键
     * @param value  缓存值
     */
    void put(String region, String key, Object value);

    /**
     * 缓存失效
     *
     * @param region 缓存区域
     * @param key    缓存键
     */
    void invalidate(String region, String ...key);

    /**
     * 缓存区域失效
     *
     * @param region 缓存区域
     */
    void invalidateRegion(String region);

    /**
     * 所有缓存失效
     */
    void invalidateAll();

    /**
     * 缓存是否存在
     *
     * @param region 缓存区域
     * @param key    缓存键
     * @return 是否存在
     */
    boolean exists(String region, String key);
}
