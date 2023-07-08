package com.dstz.auth.rest.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.auth.authentication.api.constant.AuthStatusCode;
import com.dstz.auth.core.entity.SysApplication;
import com.dstz.auth.core.entity.SysResource;
import com.dstz.auth.core.manager.SysApplicationManager;
import com.dstz.auth.core.manager.SysResourceManager;
import com.dstz.auth.rest.model.dto.SysResourceDTO;
import com.dstz.auth.rest.model.vo.SysResourceTreeVO;
import com.dstz.auth.rest.model.vo.SysResourceVO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.web.controller.AbCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dstz.auth.authentication.api.constant.AuthStatusCode.PARAM_IS_NULL;

/**
 * <p>
 * 系统权限资源定义 前端控制器
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/resource")
public class SysResourceController extends AbCrudController<SysResource> {

    @Autowired
    SysResourceManager sysResourceManager;
    @Autowired
    SysApplicationManager sysApplicationManager;


    @Override
    protected String getEntityDesc() {
        return "系统权限资源定义";
    }


    /**
     * 资源明细页面
     *
     * @param id
     * @param parentId
     * @param systemId
     * @return
     * @throws Exception ModelAndView
     */
    @RequestMapping("getJson")
    public ApiResponse<SysResourceVO> getJson(@RequestParam(value = "id", required = false) String id,
                                              @RequestParam(value = "parentId", required = false) String parentId,
                                              @RequestParam(value = "systemId", required = false) String systemId) {
        return ApiResponse.success(sysResourceManager.getResourceDetailById(id, parentId, systemId));
    }

    /**
     * 保存子系统资源信息
     *
     * @param sysResource @throws Exception void @throws
     */
    @Override
    @RequestMapping("save")
    public ApiResponse<String> save(@RequestBody SysResource sysResource) {
        return ApiResponse.success(sysResourceManager.saveSysResource(sysResource));
    }


    /**
     * 批量删除子系统资源记录
     *
     * @param id
     */
    @Override
    @RequestMapping("remove")
    public ApiResponse remove(@RequestParam("id") String id) {

        try {
            sysResourceManager.removeByResId(id);
            return ApiResponse.success("删除子系统资源成功");
        } catch (Exception e) {
            throw new BusinessMessage(AuthStatusCode.DELETE_RESOURCES_ERROR);
        }
    }

    @RequestMapping("sysResourceGet")
    public ApiResponse<SysResource> sysResourceGet(@RequestParam("id") String id) {
        SysResource sysResource = sysResourceManager.getById(id);
        return ApiResponse.success(sysResource);
    }

    /**
     * 通过应用id获取资源树
     *
     * @param systemId 应用ID
     * @return 资源树对象
     */
    @RequestMapping("getTreeData")
    public ApiResponse<List<SysResourceTreeVO>> getTreeData(@RequestParam("systemId") String systemId) {
        Assert.notEmpty(systemId, () -> new BusinessMessage(PARAM_IS_NULL.formatDefaultMessage(" systemId ")));
        return ApiResponse.success(sysApplicationManager.getTreeDataByApplication(sysApplicationManager.getById(systemId)));
    }

    /**
     * 通过应用code获取资源树
     *
     * @param systemCode 应用编码
     * @return 资源树对象
     */
    @RequestMapping("getTreeDataByCode")
    public ApiResponse<List<SysResourceTreeVO>> getTreeDataByCode(@RequestParam("systemCode") String systemCode) {
        Assert.notEmpty(systemCode, () -> new BusinessMessage(PARAM_IS_NULL.formatDefaultMessage(" systemCode ")));
        SysApplication sysApplication = sysApplicationManager.selectOne(Wrappers.lambdaQuery(SysApplication.class)
                .eq(SysApplication::getCode, systemCode));
        return ApiResponse.success(sysApplicationManager.getTreeDataByApplication(sysApplication));
    }

    @RequestMapping("getUserResource")
    public ApiResponse getUserResource(@RequestParam("userId") String userId, @RequestParam("systemId") String appId) {


        Map<String, Object> mapParam = new HashMap<>(16);
        if (StrUtil.isEmpty(appId)) {
            List<SysApplication> subsystemList = sysApplicationManager.getSystemByUser(userId);
            Assert.notNull(subsystemList, () -> new BusinessMessage(AuthStatusCode.USER_HAS_NOT_ASSIGNED_ANY_RESOURCES));
            appId = subsystemList.get(0).getId();
            mapParam.put("subsystemList", subsystemList);
        }

        List<SysResource> groupList = sysResourceManager.getByAppIdAndUserId(appId, userId);
        SysResource rootResource = new SysResource();
        rootResource.setName("菜单资源");
        rootResource.setId(NumberPool.INTEGER_ZERO.toString());
        rootResource.setAppId(appId); // 根节点
        groupList.add(rootResource);
        mapParam.put("groupList", groupList);
        return ApiResponse.success(mapParam);
    }


    @RequestMapping(value = "saveTree", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> saveTree(@RequestBody SysResourceDTO sysResource) {
        sysResourceManager.saveTree(sysResource);
        return ApiResponse.success("操作成功");
    }


}
