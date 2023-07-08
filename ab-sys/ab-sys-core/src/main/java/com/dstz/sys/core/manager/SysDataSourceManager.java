package com.dstz.sys.core.manager;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.query.AbQueryFilter;

/**
 * 系统数据源通用业务处理
 *
 * @author wacxhs
 */
public interface SysDataSourceManager {

    /**
     * 分页列表
     *
     * @param queryFilter 分页
     * @return 分页列表
     */
    PageListDTO<Object> query(AbQueryFilter queryFilter);

}
