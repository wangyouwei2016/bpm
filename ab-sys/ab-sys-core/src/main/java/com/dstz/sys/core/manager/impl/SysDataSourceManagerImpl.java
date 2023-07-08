package com.dstz.sys.core.manager.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.sys.core.manager.SysDataSourceManager;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * 系统数据源通用业务处理实现
 *
 * @author wacxhs
 */
@Service("sysDataSourceManager")
public class SysDataSourceManagerImpl implements SysDataSourceManager {

    @Override
    public PageListDTO<Object> query(AbQueryFilter queryFilter) {
        return new PageListDTO<>(
                Optional.ofNullable(queryFilter.getPage()).map(Page::getSize).orElse(10L),
                1L,
                1L,
                Collections.singletonList(ImmutableMap.of("alias", StrPool.DEFAULT_DATASOURCE_ALIAS, "name", "本地数据源"))
        );
    }
}
