package com.dstz.cms.core.manager;

import com.dstz.base.manager.AbBaseManager;
import com.dstz.cms.core.entity.CmsNotifyUser;

import java.util.List;

/**
 * <p>
 * 公告用户关联表 通用业务类
 * </p>
 *
 * @author niu
 * @since 2022-02-28
 */
public interface CmsNotifyUserManager extends AbBaseManager<CmsNotifyUser> {
    /**
     * @param notifyId 公告ID
     * @param userId   用户ID
     * @return 是否阅读等信息的对象详情
     */
    CmsNotifyUser getNotifyUser(String notifyId, String userId);

    /**
     * 获取当前用户已读的公告集合
     *
     * @return 当前用户已读的公告集合
     */
    List<String> getNotifyListByCurrentUser();

    /**
     * 删除公告相关得数据
     */
    void deleteByNotifyId(String notifyId);

}
