package com.dstz.sys.core.mapper;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.sys.core.entity.SysDailyPhrases;
import com.dstz.base.mapper.AbBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户常用语 Mapper 接口
 * </p>
 *
 * @author niu
 * @since 2022-03-14
 */
@Mapper
public interface SysDailyPhrasesMapper extends AbBaseMapper<SysDailyPhrases> {

    /**
     * 常用语的分页接口 (系统内置的常用语或自己的常用语)
     */
    PageListDTO<SysDailyPhrases> listJson(AbQueryFilter filter);
}
