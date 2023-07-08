package com.dstz.cms.core.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.cms.core.entity.CmsNotifyUser;
import com.dstz.cms.core.manager.CmsNotifyUserManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 公告用户关联表 通用服务实现类
 *
 * @author niu
 * @since 2022-02-28
 */
@Service("cmsNotifyUserManager")
public class CmsNotifyUserManagerImpl extends AbBaseManagerImpl<CmsNotifyUser> implements CmsNotifyUserManager {

    /**
     * 获取指定公告ID指定用户ID的关联详情
     *
     * @param notifyId 公告ID
     * @param userId   用户ID
     * @return CmsNotifyUser 是否已读公告以及已读时间的关联对象
     */
    @Override
    public CmsNotifyUser getNotifyUser(String notifyId, String userId) {
        return getBaseMapper().selectOne(Wrappers.<CmsNotifyUser>lambdaQuery()
                .eq(CmsNotifyUser::getNotifyId, notifyId)
                .eq(CmsNotifyUser::getUserId, userId));
    }

    /**
     * 获取当前用户已读的公告集合
     *
     * @return 当前用户已读的公告集合
     */
    @Override
    public List<String> getNotifyListByCurrentUser() {
        return selectByWrapper(Wrappers.<CmsNotifyUser>lambdaQuery()
                .eq(CmsNotifyUser::getUserId, UserContextUtils.getUserId())).stream()
                .map(CmsNotifyUser::getNotifyId).collect(Collectors.toList());
    }

    /**
     * 删除公告相关得数据
     */
    @Override
    public void deleteByNotifyId(String notifyId) {
        remove(Wrappers.<CmsNotifyUser>lambdaQuery().eq(CmsNotifyUser::getNotifyId, notifyId));
    }

}
