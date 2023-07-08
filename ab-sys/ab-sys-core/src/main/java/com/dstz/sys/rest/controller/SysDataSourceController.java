package com.dstz.sys.rest.controller;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.sys.core.manager.SysDataSourceManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 系统数据源控制器
 *
 * @author wacxhs
 */
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/dataSource")
@RestController
public class SysDataSourceController {

    private final SysDataSourceManager sysDataSourceManager;

    public SysDataSourceController(SysDataSourceManager sysDataSourceManager) {
        this.sysDataSourceManager = sysDataSourceManager;
    }

    /**
     * 数据分页列表
     *
     * @param queryParamDTO 查询参数
     * @return 接口处理结果-数据源分页
     */
    @PostMapping(value = "/listJson", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> listJson(@Valid @RequestBody QueryParamDTO queryParamDTO) {
        PageListDTO<Object> pageListDTO = sysDataSourceManager.query(new DefaultAbQueryFilter(queryParamDTO));
        return ApiResponse.success(pageListDTO);
    }
}
