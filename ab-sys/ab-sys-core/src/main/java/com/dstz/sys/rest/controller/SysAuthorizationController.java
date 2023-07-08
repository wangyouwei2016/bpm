package com.dstz.sys.rest.controller;


import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.sys.api.constant.RightsObjectConstants;
import com.dstz.sys.rest.model.dto.AuthorizationDTO;
import com.dstz.sys.core.entity.SysAuthorization;
import com.dstz.sys.core.manager.SysAuthorizationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dstz.base.api.vo.ApiResponse.success;

/**
 * <p>
 * 通用资源授权配置 前端控制器
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/authorization")
public class SysAuthorizationController extends AbCrudController<SysAuthorization> {

    public final SysAuthorizationManager sysAuthorizationManager;

    public SysAuthorizationController(SysAuthorizationManager sysAuthorizationManager) {
        this.sysAuthorizationManager = sysAuthorizationManager;
    }

    /**
     * 保存授权结果
     *
     * @param saveDTO 参数对象
     */
    @RequestMapping("saveAuthorization")
    public ApiResponse<String> saveAuthorization(@RequestBody AuthorizationDTO saveDTO) {
        return success(() -> sysAuthorizationManager.createAll(saveDTO));
    }

    /**
     * 获取授权结果用来初始化
     *
     * @param dto 要获取授权对象的参数dto
     */
    @PostMapping("getAuthorizations")
    public ApiResponse<List<SysAuthorization>> getAuthorizations(@RequestBody AuthorizationDTO dto) {
        return success(sysAuthorizationManager.getByTarget(dto.getRightsObject(), dto.getRightsTarget()));
    }

    @Override
    protected String getEntityDesc() {
        return "通用资源授权配置";
    }
}
