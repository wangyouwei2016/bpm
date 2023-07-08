package com.dstz.cms.core.manager;

import com.dstz.base.manager.AbBaseManager;
import com.dstz.cms.core.entity.CmsFrequentUsed;

import java.util.List;

/**
 * <p>
 * 常用流程管理 通用业务类
 * </p>
 *
 * @author niu
 * @since 2022-03-11
 */
public interface CmsFrequentUsedManager extends AbBaseManager<CmsFrequentUsed> {

    /**
     * 批量新增常用流程
     *
     * @param resourceIdList 常用流程ID集合
     */
    void saveBatch(List<String> resourceIdList);
}
