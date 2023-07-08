package com.dstz.base.common.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 上下文清理工具类
 *
 * @author wacxhs
 */
public class ContextCleanUtils {

    private ContextCleanUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * 时机
     */
    public enum Phase {

        /**
         * 线程运行前后
         */
        THREAD,

        /**
         * web请求后
         */
        REQUEST_COMPLETE
    }

    private final static Map<Phase, List<Runnable>> CONTEXT_CLEAN_REGISTRY = MapUtil.newHashMap();

    /**
     * 注册清理方法
     *
     * @param runnable 清理运行方法
     */
    public static void register(Runnable runnable, Phase... phases) {
        Assert.state(ArrayUtil.isNotEmpty(phases), "phases parameter must");
        for (Phase phase : phases) {
            CONTEXT_CLEAN_REGISTRY.computeIfAbsent(phase, k -> new LinkedList<>()).add(runnable);
        }
    }

    /**
     * 执行清理，根据时机调用
     *
     * @param phases 执行清理
     */
    public static void execute(Phase... phases) {
        Assert.state(phases != null, "phases parameter must");
        // 调用一次
        HashSet<Runnable> onceHashSet = new HashSet<>();
        for (Phase phase : phases) {
            CONTEXT_CLEAN_REGISTRY.getOrDefault(phase, Collections.emptyList()).stream().filter(onceHashSet::add).forEach(Runnable::run);
        }
    }

    /**
     * 执行清理所有
     */
    public static void executeAll() {
        execute(Phase.values());
    }
}
