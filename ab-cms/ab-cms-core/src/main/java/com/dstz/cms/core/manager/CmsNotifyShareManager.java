package com.dstz.cms.core.manager;

import com.dstz.base.common.identityconvert.SysIdentity;
import com.dstz.base.manager.AbBaseManager;
import com.dstz.cms.core.entity.CmsNotifyShare;
import com.dstz.cms.core.entity.dto.CmsNotifyDTO;

import java.util.List;

/**
 * <p>
 * 公告发布对应部门表 通用业务类
 * </p>
 *
 * @author niu
 * @since 2022-03-01
 */
public interface CmsNotifyShareManager extends AbBaseManager<CmsNotifyShare> {

    /**
     * 保存组织和公告的关联关系
     *
     * @param cmsNotifyDTO 公告对象
     */
    void saveRelation(CmsNotifyDTO cmsNotifyDTO);

    /**
     * 通过公告ID删除关联的组织关系
     *
     * @param cmsNotifyId 公告ID
     */
    void deleteByNotifyId(String cmsNotifyId);

    /**
     * 获取当前用户获取关联的公告ID集合
     *
     */
    List<String> getNotifyListByCurrentUser();

    /**
     * 获取公告关联的用户id集合
     *
     */
    List<SysIdentity> getUserListByNotifyId(String cmsNotifyId);

}
