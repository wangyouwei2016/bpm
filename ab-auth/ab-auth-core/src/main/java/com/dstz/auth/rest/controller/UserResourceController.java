package com.dstz.auth.rest.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.auth.authentication.api.constant.AuthStatusCode;
import com.dstz.auth.authentication.api.constant.ResouceTypeConstant;
import com.dstz.auth.authentication.api.model.ISysApplication;
import com.dstz.auth.core.entity.SysApplication;
import com.dstz.auth.core.entity.SysResource;
import com.dstz.auth.core.manager.SysApplicationManager;
import com.dstz.auth.core.manager.SysResourceManager;
import com.dstz.auth.rest.model.vo.SysResourceTreeVO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.BeanConversionUtils;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.org.api.GroupApi;
import com.dstz.org.api.enums.GroupType;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 用户资源
 *
 * @author lightning
 * @since 2022-02-07
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/userResource")
public class UserResourceController {

    @Autowired
    private SysResourceManager sysResourceManager;

    @Autowired
    private GroupApi groupApi;

    @Autowired
    private SysApplicationManager sysApplicationManager;

    @RequestMapping(value = "/userMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<?> userMsg() {
        return userMsg(false);
    }

    @RequestMapping(value = "/appUserMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<?> appUserMsg() {
        return userMsg(true);
    }

    private ApiResponse<?> userMsg(boolean isMobile) {
        final IUser currentUser = UserContextUtils.getUser().orElseThrow(() -> new BusinessException(GlobalApiCodes.LOGIN_INVALID));

        //获取当前用户可用的应用列表
        List<SysApplication> sysApplications;

        // 筛选出不同端的应用列表
        if (isMobile) {
            sysApplications = CollUtil.filter(sysApplicationManager.getCurrentUserSystem(), item -> NumberPool.INTEGER_ONE.equals(item.getAppType()));
        } else {
            sysApplications = CollUtil.filter(sysApplicationManager.getCurrentUserSystem(), item -> !NumberPool.INTEGER_ONE.equals(item.getAppType()));
        }

        if (CollectionUtil.isEmpty(sysApplications)) {
            throw new BusinessMessage(AuthStatusCode.USER_HAS_NOT_ASSIGNED_ANY_RESOURCES);
        }

        ISysApplication currentApplication = getCurrentUserApp(isMobile, currentUser , sysApplications);


        Map<String, Object> map = MapUtil.newHashMap();
        map.put("currentEnviroment", SpringUtil.getActiveProfile());
        map.put("subsystemList", sysApplications);
        map.put("currentSystem", currentApplication.getCode());
        map.put("username", currentUser.getFullName());
        map.put("currentOrg", UserContextUtils.getGroup().orElse(null));
        map.put("orgList", groupApi.getByGroupTypeAndUserId(GroupType.ORG.getType(), currentUser.getUserId()));
        map.put("user", userModel(currentUser));
        map.put("pwdIsexpire", getUserPwdIsExpired());

        getSysResource(map, currentApplication.getId(), currentUser.getUserId());

        return ApiResponse.success(map);
    }


    private ISysApplication getCurrentUserApp(boolean isMobile, IUser currentUser, List<SysApplication> sysApplications) {
        // TODO 取切换后的应用名称
        return sysApplications.stream()
                .filter(sysApplication -> NumberPool.BOOLEAN_TRUE.equals(sysApplication.getIsDefault()))
                .findFirst()
                .orElseGet(() -> CollUtil.getFirst(sysApplications));
    }

    private Map<String, Object> userModel(IUser user) {
        Map<String, Object> userModel = BeanCopierUtils.transformMap(user, IUser.class);
        // 签名
        userModel.put("signature", user.getAttrValue("signature", String.class));
        // 头像
        userModel.put("photo", user.getAttrValue("photo", String.class));
        return userModel;
    }


    /**
     * <12 只是12毫秒 ,用户到期时间小于 当前时间 则视为需要重置密码
     *
     * @return Boolean
     */
    private Boolean getUserPwdIsExpired() {
        Optional<IUser> user = UserContextUtils.getUserContext().getUser();
        Date expireDate = user.map(o -> o.getAttrValue(IUser.ATTR_EXPIRE_DATE, Date.class)).orElse(null);
        return expireDate != null && expireDate.getTime() - System.currentTimeMillis() > 0;
    }

    private void getSysResource(Map<String, Object> map, String appId, String userId) {
        List<SysResource> sysResourceList;
        if (UserContextUtils.isSuperAdmin()) {
            sysResourceList = sysResourceManager.getBySystemId(appId);
        } else {
            sysResourceList = sysResourceManager.getByAppIdAndUserId(appId, userId);
        }
        // 菜单和按钮分离
        List<SysResourceTreeVO> menuList = new ArrayList<>();
        Map<String, Boolean> buttonPermission = MapUtil.newHashMap();
        List<SysResourceTreeVO> sysResources = BeanCopierUtils.transformList(sysResourceList, SysResourceTreeVO.class);
        for (SysResourceTreeVO sysResource : sysResources) {
            if (ResouceTypeConstant.MENU.getKey().equals(sysResource.getType())) {
                menuList.add(sysResource);
            } else {
                buttonPermission.put(sysResource.getCode(), NumberPool.INTEGER_ONE.equals(sysResource.getEnable()));
            }
        }
        map.put("userMenuList", BeanConversionUtils.listToTree(menuList));
        map.put("buttonPermission", buttonPermission);
    }

    /**
     * 切换应用
     *
     * @param appCode 应用编码
     * @return 接口响应
     */
    @RequestMapping(value = "/switchApp/{appCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> switchApp(@PathVariable("appCode") String appCode, @RequestParam(value = "isMobile", defaultValue = "false", required = false) boolean isMobile) {
        SysApplication sysApplication = CollUtil.findOne(sysApplicationManager.getCurrentUserSystem(), item -> StrUtil.equalsIgnoreCase(appCode, item.getCode()));
        Assert.notNull(sysApplication, () -> new BusinessMessage(AuthStatusCode.APPLICATION_NO_PERMISSIONS.formatDefaultMessage(appCode)));
        List<SysResource> sysResourceList;
        if (UserContextUtils.isSuperAdmin()) {
            sysResourceList = sysResourceManager.getBySystemId(sysApplication.getId());
        } else {
            sysResourceList = sysResourceManager.getByAppIdAndUserId(sysApplication.getId(), UserContextUtils.getUserId());
        }
        List<SysResourceTreeVO> menuList = new ArrayList<>();
        List<SysResourceTreeVO> sysResources = BeanCopierUtils.transformList(sysResourceList, SysResourceTreeVO.class);
        for (SysResourceTreeVO sysResource : sysResources) {
            if (ResouceTypeConstant.MENU.getKey().equals(sysResource.getType()) && sysResource.getEnable().equals(NumberPool.INTEGER_ONE)) {
                menuList.add(sysResource);
            }
        }
        if (menuList.size() == NumberPool.INTEGER_ZERO) {
            return AuthStatusCode.APP_NO_RESOURCE.formatDefaultMessage(sysApplication.getName()).buildApiResponse();
        }
        // 打开方式为0（跳转），需检查是否有菜单资源
        if (StrPool.NUMBER_ZERO.equals(sysApplication.getOpenType())) {
            if (sysResourceManager.selectCount(Wrappers.lambdaQuery(SysResource.class).eq(SysResource::getAppId, sysApplication.getId())) <= NumberPool.INTEGER_ZERO) {
                return AuthStatusCode.APP_NO_RESOURCE.formatDefaultMessage(sysApplication.getName()).buildApiResponse();
            }
        }
        // TODO 切换应用实现
        return ApiResponse.success();
    }

    /**
     * 切换组织
     *
     * @param orgCode 组织编码
     * @return 接口响应
     */
    @RequestMapping(value = "/switchOrg/{orgCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Void> switchOrg(@PathVariable("orgCode") String orgCode) {
        final IUser currentUser = UserContextUtils.getValidUser();
        List<? extends IGroup> orgList = groupApi.getByGroupTypeAndUserId(GroupType.ORG.getType(), currentUser.getUserId());
        if (CollUtil.isEmpty(orgList)) {
            return AuthStatusCode.USER_UNABSORBED_ORG.buildApiResponse();
        }
        IGroup org = CollUtil.findOne(orgList, filterItem -> StrUtil.equals(filterItem.getGroupCode(), orgCode));
        if (org == null) {
            return AuthStatusCode.ILLEGAL_CURRENT_ORG.formatDefaultMessage(orgCode).buildApiResponse();
        }
        // TODO 切换组织实现
        return ApiResponse.success();
    }
}
