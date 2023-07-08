package com.dstz.cms.core.manager;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.manager.AbBaseManager;
import com.dstz.cms.core.entity.CmsNotify;
import com.dstz.cms.core.entity.dto.CmsNotifyDTO;
import com.dstz.cms.core.entity.vo.CmsNotifyListVO;
import com.dstz.cms.core.entity.vo.CmsNotifyVO;

import java.util.List;

/**
 * <p>
 * 系统公告表 通用业务类
 * </p>
 *
 * @author niu
 * @since 2022-02-28
 */
public interface CmsNotifyManager extends AbBaseManager<CmsNotify> {

    /**
     * 分页查询
     *
     * @param queryParamDto 查询对象
     */
    PageListDTO<CmsNotifyVO> page(QueryParamDTO queryParamDto);

    /**
     * 查询指定ID的公告内容
     *
     * @param id 公告ID
     * @return CmsNotifyDTO 展示的公告详情
     */
    CmsNotifyVO details(String id);

    /**
     * 创建公告
     *
     * @param cmsNotifyDTO 新增的公告DTO对象
     */
    void save(CmsNotifyDTO cmsNotifyDTO);

    /**
     * 修改公告
     *
     * @param cmsNotifyDTO 修改的的公告DTO对象
     */
    void update(CmsNotifyDTO cmsNotifyDTO);

    /**
     * 查看所属公告列表 (筛选公告关联的组织)
     */
    PageListDTO<CmsNotifyListVO> getNotifyPage(QueryParamDTO queryParamDto);

    /**
     * 查询用户未读公告数量
     */
    int queryUnReadCount();

    /**
     * 发布公告
     *
     * @param id 公告id
     */
    void releaseNotify(String id);

    /**
     * 下架公告
     *
     * @param id 公告id
     */
    void withdrawNotify(String id);

}
