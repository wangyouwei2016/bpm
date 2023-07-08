package com.dstz.sys.core.mapper;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.mapper.AbBaseMapper;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.sys.core.entity.SysLogErr;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统异常日志 Mapper 接口
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@Mapper
public interface SysLogErrMapper extends AbBaseMapper<SysLogErr> {

    /**
     * queryFilter 分页列表查询
     * @param abQueryFilter 查询参数
     * @return
     */
    PageListDTO<SysLogErr> query(AbQueryFilter abQueryFilter);

}
