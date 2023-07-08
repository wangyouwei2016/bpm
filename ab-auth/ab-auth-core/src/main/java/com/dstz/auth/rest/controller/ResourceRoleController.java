package com.dstz.auth.rest.controller;


import cn.hutool.core.util.StrUtil;
import com.dstz.auth.authentication.api.constant.AuthStatusCode;
import com.dstz.auth.core.entity.ResourceRole;
import com.dstz.auth.core.manager.ResourceRoleManager;
import com.dstz.auth.core.manager.SysApplicationManager;
import com.dstz.auth.core.manager.SysResourceManager;
import com.dstz.auth.rest.model.dto.GrantRoleResourceDTO;
import com.dstz.auth.rest.model.vo.SysResourceTreeVO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.web.controller.AbCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 资源角色关联表 前端控制器
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/resRole")
public class ResourceRoleController extends AbCrudController<ResourceRole> {

    @Autowired
    ResourceRoleManager resourceRoleManager;

    @Autowired
    SysResourceManager sysResourceManager;

    @Autowired
    SysApplicationManager sysApplicationManager;

    @Override
    protected String getEntityDesc() {
        return "资源角色关联表";
    }


    /**
     * 角色资源分配明细页面
     *
     * @param id
     * @return
     * @throws Exception ModelAndView
     */
    @RequestMapping(value = "getJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<ResourceRole> getJson(@RequestParam("id") String id) throws Exception {
        if (StrUtil.isEmpty(id)) {
            throw new BusinessMessage(AuthStatusCode.PARAM_IS_NULL.formatDefaultMessage("id"));
        }
        return ApiResponse.success(resourceRoleManager.getById(id));
    }


    @RequestMapping("getTreeData")
    public ApiResponse<List<SysResourceTreeVO>> getTreeData(@RequestParam("roleId") String roleId, @RequestParam("systemId") String systemId) throws Exception {
        return ApiResponse.success(resourceRoleManager.getRoleRes(systemId, roleId));
    }


    @RequestMapping("getRoleResTreeData")
    public ApiResponse<List<SysResourceTreeVO>> getRoleResTreeData(@RequestParam("roleId") String roleId, @RequestParam("code") String code) throws Exception {
        return ApiResponse.success(resourceRoleManager.getRoleResTreeData(roleId, code));
    }

    /**
     * 角色资源分配
     *
     * @param dto 分配数据对象
     * @return 接口处理结果
     */
    @RequestMapping(value = "grantRoleResource")
    public ApiResponse<Void> grantRoleResource(@Valid @RequestBody GrantRoleResourceDTO dto) {
        resourceRoleManager.grantRoleResource(dto);
        return ApiResponse.success();
    }

}
