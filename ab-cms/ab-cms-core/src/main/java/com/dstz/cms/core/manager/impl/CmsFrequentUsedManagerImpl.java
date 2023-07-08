package com.dstz.cms.core.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.cms.core.entity.CmsFrequentUsed;
import com.dstz.cms.core.manager.CmsFrequentUsedManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 常用流程管理 通用服务实现类
 *
 * @author niu
 * @since 2022-03-11
 */
@Service("cmsFrequentUsedManager")
public class CmsFrequentUsedManagerImpl extends AbBaseManagerImpl<CmsFrequentUsed> implements CmsFrequentUsedManager {

    /**
     * 获取当前用户的所有常用流程
     *
     * @return List<CmsFrequentUsed> 常用流程集合
     */
    @Override
    public List<CmsFrequentUsed> list() {
        return selectByWrapper(Wrappers.<CmsFrequentUsed>lambdaQuery().eq(CmsFrequentUsed::getUserId, UserContextUtils.getUserId()));
    }

    /**
     * 批量新增常用流程
     *
     * @param defIdList 常用流程ID集合
     */
    @Override
    public void saveBatch(List<String> defIdList) {
        defIdList.forEach(s -> create(new CmsFrequentUsed(UserContextUtils.getUserId(), s)));
    }
}
