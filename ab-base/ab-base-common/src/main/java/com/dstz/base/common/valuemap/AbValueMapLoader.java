package com.dstz.base.common.valuemap;

import java.util.Collection;
import java.util.Map;

/**
 * ab 值映射加载器
 *
 * @author wacxhs
 */
public interface AbValueMapLoader<K, V> {

    /**
     * 加载映射
     *
     * @param abValueMap 值映射器
     * @param mapKeys    映射键
     * @return 关联值，值应返回Map或者对象
     */
    Map<K, V> loading(AbValueMap abValueMap, Collection<K> mapKeys);

}
