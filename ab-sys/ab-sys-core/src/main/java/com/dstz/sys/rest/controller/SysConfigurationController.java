package com.dstz.sys.rest.controller;


import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.sys.core.manager.SysConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dstz.base.web.controller.AbCrudController;
import com.dstz.sys.core.entity.SysConfiguration;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lightning
 * @since 2023-05-11
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/sysConfiguration")
public class SysConfigurationController extends AbCrudController<SysConfiguration> {

    @Override
    protected String getEntityDesc() {
        return "";
    }

    @Autowired
    SysConfigurationManager sysConfigurationManager;
    /**
     * 根据编码获取系统配置
     * @param code 配置编码
     * @return 配置信息
     */
    @GetMapping({"/getConfByCode/{code}"})
    public ApiResponse<String> getConfByCode(@PathVariable String code) {
        return ApiResponse.success(sysConfigurationManager.getConfByCode(code));
    }

}
