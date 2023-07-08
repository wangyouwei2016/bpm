package com.dstz.cms.core.manager;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.manager.AbBaseManager;
import com.dstz.cms.core.entity.CmsNews;
import com.dstz.cms.core.entity.dto.CmsNewsDTO;

import java.util.List;

/**
 * <p>
 * 新闻表 通用业务类
 * </p>
 *
 * @author niu
 * @since 2022-03-04
 */
public interface CmsNewsManager extends AbBaseManager<CmsNews> {
    /**
     * 按实体ID获取实体
     *
     * @param id 实体ID
     * @return 实体记录
     */
    CmsNewsDTO details(String id);

    /**
     * 新增或修改新闻
     *
     * @param cmsNews 新闻对象
     */
    void saveOrUpdate(CmsNews cmsNews);

    /**
     * 发布新闻
     *
     * @param id 新闻id
     */
    void releaseNews(String id);

    /**
     * 下架新闻
     *
     * @param id 新闻id
     */
    void withdrawNews(String id);

    /**
     * 分页列表
     */
    PageListDTO<CmsNews> listJson(QueryParamDTO queryParamDto);

    /**
     * 首页获取固定两条,精简后的新闻(剔除附件,和其他无用字段)
     */
    PageListDTO<CmsNews> getNewsPage(QueryParamDTO queryParamDto);
}
