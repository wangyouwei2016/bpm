package com.dstz.sys.rest.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.encrypt.EncryptUtil;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.property.SysPropertyService;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.sys.api.constant.SysApiCodes;
import com.dstz.sys.core.entity.SysProperties;
import com.dstz.sys.core.manager.SysPropertiesManager;
import org.springframework.web.bind.annotation.*;

/**
 * @author jinxia.hou
 * @Name SysPropertiesController
 * @description: 系统属性
 * @date 2022/2/1610:41
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/properties")
public class SysPropertiesController extends AbCrudController<SysProperties> {

    private final SysPropertiesManager sysPropertiesManager;

    private final SysPropertyService sysPropertyService;

    public SysPropertiesController(SysPropertiesManager sysPropertiesManager, SysPropertyService sysPropertyService) {
        this.sysPropertiesManager = sysPropertiesManager;
        this.sysPropertyService = sysPropertyService;
    }

    /**
     * 保存系统属性信息
     *
     * @param sysProperties
     * @throws Exception void
     * @throws
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @Override
    public ApiResponse<String> save(@RequestBody SysProperties sysProperties) {
        Assert.isFalse(sysPropertiesManager.isExist(sysProperties), () -> new BusinessMessage(SysApiCodes.KEY_WORD_DUPLICATE.formatDefaultMessage(SysApiCodes.KEY_WORD_DUPLICATE.getMessage(), "别名" + sysProperties.getCode())));

        if (sysProperties.getEncrypt() != null && sysProperties.getEncrypt() == 1) {
            sysProperties.setValue(EncryptUtil.encrypt(sysProperties.getValue()));
        }

        if (StrUtil.isEmpty(sysProperties.getId())) {
            sysPropertiesManager.create(sysProperties);
            sysPropertiesManager.reloadProperty();
            return ApiResponse.success("添加系统属性成功");
        }

        sysPropertiesManager.update(sysProperties);
        sysPropertiesManager.reloadProperty();
        return ApiResponse.success("更新系统属性成功");

    }

    /**
     * 保存系统属性信息
     *
     * @param code
     * @throws Exception void
     * @throws
     */
    @RequestMapping(value = "getByCode")
    public ApiResponse<Object> getByCode(@RequestParam("code") String code) {
        String result = sysPropertyService.getValByCode(code);
        return ApiResponse.success(result);
    }


    @Override
    protected String getEntityDesc() {
        return "系统属性";
    }
}
