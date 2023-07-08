package com.dstz.auth.rest.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.auth.authentication.api.constant.AuthCacheKeyConstant;
import com.dstz.auth.authentication.api.constant.AuthStatusCode;
import com.dstz.auth.core.entity.SysApplication;
import com.dstz.auth.core.manager.SysApplicationManager;
import com.dstz.auth.core.mapper.SysApplicationMapper;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.cache.ICache;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.web.controller.AbCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.dstz.base.api.vo.ApiResponse.success;

/**
 * 系统应用 前端控制器
 *
 * @author lightning
 * @since 2022-02-07
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/application")
public class SysApplicationController extends AbCrudController<SysApplication> {


    @Autowired
    SysApplicationManager sysApplicationManager;

    @Autowired
    SysApplicationMapper sysApplicationMapper;

    @Autowired
    ICache iCache;


    @Override
    protected String getEntityDesc() {
        return "应用";
    }

    /**
     * 分页查询按时间排序
     *
     * @param queryParamDto 查询条件
     * @return 查询结果
     */
    @Override
    @RequestMapping(value = "listJson")
    public ApiResponse<?> listJson(@Valid @RequestBody QueryParamDTO queryParamDto) {
        queryParamDto.setSortColumn("createTime");
        queryParamDto.setSortOrder("desc");
        return super.listJson(queryParamDto);
    }


    /**
     * 移动端并且启用的应用列表
     *
     * @return 查询结果
     */
    @GetMapping(value = "mobileApplication")
    public ApiResponse<List<SysApplication>> mobileApplication() {
        return success(sysApplicationManager.selectByWrapper(Wrappers.lambdaQuery(SysApplication.class)
                .eq(SysApplication::getEnabled, 1)
                .eq(SysApplication::getAppType, 1)));
    }


    @RequestMapping(value = "getUserSystem", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<List> getUserSystem() {
        return success(sysApplicationMapper.selectList(new QueryWrapper<>()));
    }

    /**
     * 子系统定义明细页面(查询指定ID)
     *
     * @param id
     * @return SysApplication
     * @throws Exception ModelAndView
     */
    @RequestMapping("getJson")
    public ApiResponse<SysApplication> getJson(@NotBlank(message = "id不能为空") @RequestParam("id") String id) {
        SysApplication subsystem = sysApplicationMapper.selectById(id);
        return success(subsystem);
    }

    /**
     * @param sysApplication
     * @return 保存子系统定义信息
     * @throws Exception void
     * @throws
     */
    @RequestMapping("save")
    @Override
    public ApiResponse<String> save(@RequestBody SysApplication sysApplication) {
        String resultMsg = null;
        Assert.isFalse(sysApplicationManager.isExist(sysApplication), () -> new BusinessMessage(AuthStatusCode.RESOURCES_CODE_REPEAT.formatDefaultMessage(sysApplication.getCode())));

        if (StrUtil.isEmpty(sysApplication.getId())) {
            sysApplicationMapper.insert(sysApplication);
            resultMsg = "添加子系统定义成功";
        } else {
            sysApplicationManager.updateById(sysApplication);
            resultMsg = "更新子系统定义成功";
        }
        iCache.invalidate(AuthCacheKeyConstant.CACHE_REGION_AUTH_CLIENT, AuthCacheKeyConstant.AUTH_APP_LIST_EL);
        return success(resultMsg);
    }

}
