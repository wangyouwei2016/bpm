package com.dstz.sys.rest.controller;


import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.sys.core.entity.SysLogErr;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统异常日志 前端控制器
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/logErr")

public class SysLogErrController extends AbCrudController<SysLogErr> {

    @Override
    protected String getEntityDesc() {
        return "系统异常日志";
    }
}
