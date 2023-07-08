package com.dstz.cms.core.mapper;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.mapper.AbBaseMapper;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.cms.core.entity.CmsNews;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 新闻表 Mapper 接口
 * </p>
 *
 * @author niu
 * @since 2022-03-04
 */
@Mapper
public interface CmsNewsMapper extends AbBaseMapper<CmsNews> {

    /**
     * 分页查询
     *
     * @param defaultAbQueryFilter 查询条件
     * @return 分页数据
     */
    PageListDTO<CmsNews> listJson(DefaultAbQueryFilter defaultAbQueryFilter);

    /**
     * 首页获取精简后的新闻(剔除附件,和其他无用字段)
     *
     * @param defaultAbQueryFilter 查询条件
     * @return 新闻集合
     */
    PageListDTO<CmsNews> getNewsPage(DefaultAbQueryFilter defaultAbQueryFilter);

}
