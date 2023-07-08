package com.dstz.org.rest.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.api.vo.PageListVO;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.events.AbUserEvent;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.property.PropertyEnum;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.org.core.constant.OrgStatusCode;
import com.dstz.org.core.entity.OrgUser;
import com.dstz.org.core.manager.OrgUserManager;
import com.dstz.org.dto.*;
import com.dstz.org.vo.OrgUserInfoVO;
import com.dstz.org.vo.OrgUserListJsonVO;
import com.dstz.org.vo.OrgUserVO;
import com.dstz.org.vo.ResourceUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.dstz.base.api.vo.ApiResponse.fail;
import static com.dstz.base.api.vo.ApiResponse.success;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xz
 * @since 2022-02-07
 */
@RestController
@RequestMapping(AbAppRestConstant.ORG_SERVICE_PREFIX + "/user")
public class OrgUserController extends AbCrudController<OrgUser> {

    /**
     * 允许 fullname，account, status作为入参传入列表页
     */
    final Set<String> accessQueryFilters = CollUtil.newHashSet("fullname", "account", "sex", "status", "mobile");
    @Autowired
    OrgUserManager orgUserManager;

    @Override
    public Set<String> getAccessQueryFilters() {
        return accessQueryFilters;
    }

    /**
     * 保存用户信息
     *
     * @param user 保存用户DTO
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "saveUserDto")
    public ApiResponse<Object> saveUserDto(@Valid @RequestBody SaveOrgUserDTO user) {
        //保存用户所有信息
        orgUserManager.saveUserDto(user);
        // 当前用户修改了自己的信息，需要更新缓存数据
        if (StrUtil.equals(UserContextUtils.getUserId(), user.getId())) {
            SpringUtil.publishEvent(new AbUserEvent(CollectionUtil.newArrayList(UserContextUtils.getAccount()), AbUserEvent.EventType.UPDATE_USER));
        }
        return success().withMessage("保存用户成功");
    }

    /**
     * 个人中心查询个人信息接口
     *
     * @return 用户信息
     */
    @RequestMapping(value = "getUserInfo")
    public ApiResponse<OrgUserInfoVO> getUserInfo() {
        return success(orgUserManager.getUserInfo());
    }

    @RequestMapping(value = "saveUserInfo")
    public ApiResponse<Object> saveUserInfo(@Valid @RequestBody SaveOrgUserInfoDTO user) {
        //保存用户所有信息
        orgUserManager.saveUserInfo(user);
        // 当前用户修改了自己的信息，需要更新缓存数据
        SpringUtil.publishEvent(new AbUserEvent(CollectionUtil.newArrayList(UserContextUtils.getAccount()), AbUserEvent.EventType.UPDATE_USER));
        return success().withMessage("保存用户成功");
    }

    /**
     * 获取用户详情
     *
     * @param id 用户id
     * @return 用户信息
     */
    @RequestMapping(value = "getUserVO")
    public ApiResponse<OrgUserVO> getUserVO(@NotBlank(message = "参数不能为空") @RequestParam(name = "id") String id) {
        return success(orgUserManager.getUserVO(id));
    }

    /**
     * 重置密码接口
     *
     * @param id 用户id
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "resetUserPassword")
    public ApiResponse<Object> resetUserPassword(@NotBlank(message = "参数不能为空") @RequestParam(name = "id") String id) {
        if (!UserContextUtils.isSuperAdmin()) {
            return fail(OrgStatusCode.IS_SUPER_ADMIN.getCode(), OrgStatusCode.IS_SUPER_ADMIN.getMessage());
        }
        orgUserManager.resetUserPassword(id);
        // 当前用户修改了自己的信息，需要更新缓存数据
        if (StrUtil.equals(UserContextUtils.getUserId(), id)) {
            SpringUtil.publishEvent(new AbUserEvent(CollectionUtil.newArrayList(UserContextUtils.getAccount()), AbUserEvent.EventType.UPDATE_USER));
        }
        return success().withMessage("密码已经重置为1");
    }

    /**
     * 修改用户状态
     *
     * @param id 用户id
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "updateUserStatus")
    public ApiResponse<String> updateUserStatus(@NotBlank(message = "参数不能为空") @RequestParam(name = "id") String id) {
        checkIsDemoEnvironment();
    	orgUserManager.updateUserStatus(id);
        // 当前用户修改了自己的信息，需要更新缓存数据
        if (StrUtil.equals(UserContextUtils.getUserId(), id)) {
            SpringUtil.publishEvent(new AbUserEvent(CollectionUtil.newArrayList(UserContextUtils.getAccount()), AbUserEvent.EventType.UPDATE_USER));
        }
        return success();
    }

    /**
     * 用户更新密码
     *
     * @param updateUserPassWorldDTO 更新密码DTO
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "updateUserPassWorld")
    public ApiResponse<String> updateUserPassWorld(@Valid @RequestBody UpdateUserPassWorldDTO updateUserPassWorldDTO) {
    	checkIsDemoEnvironment();
        orgUserManager.updateUserPassWorld(updateUserPassWorldDTO);
        // 当前用户修改了自己的信息，需要更新缓存数据
        SpringUtil.publishEvent(new AbUserEvent(CollectionUtil.newArrayList(UserContextUtils.getAccount()), AbUserEvent.EventType.UPDATE_USER));
        return success();
    }

    /**
     * 获取密码校验正则
     *
     * @return 密码检验规则
     */
    @RequestMapping(value = "getPwdCheckRule")
    public ApiResponse<Map<String, String>> getPwdCheckRule() {
        return success(Collections.singletonMap(PropertyEnum.PWD_CHECK_RULE_KEY.getPropertyValue(String.class), PropertyEnum.PWD_CHECK_RULE_TXT.getPropertyValue(String.class)));
    }

    /**
     * 修改密码是否退出登录
     *
     * @return Boolean值
     */
    @RequestMapping(value = "updatePwdIsLogOut")
    public ApiResponse<Boolean> updatePwdIsLogOut() {
        return success(PropertyEnum.CHANGE_PWD_iS_LOG_OUT.getPropertyValue(Boolean.class) || PropertyEnum.CHANGE_PWD_iS_Exit_SYSTEM.getPropertyValue(Boolean.class));
    }


    public void removeCheck(List<String> ids) {
        List<String> accounts = StrUtil.split(PropertyEnum.ADMIN_ACCOUNTS.getPropertyValue(String.class), CharUtil.COMMA);
        for (String userId : ids) {
            OrgUserVO userVO = orgUserManager.getUserVO(userId);
            if (accounts.contains(userVO.getAccount())) {
                throw new BusinessMessage(OrgStatusCode.NOT_ALLOW_DELETE_ADMIN);
            }
        }
    }


    /**
     * 用户权限查询接口
     *
     * @param queryParamDto 查询参数DTO
     * @return 用户权限查询结果
     */
    @RequestMapping(value = "getUserByResource")
    public ApiResponse<PageListVO<ResourceUserVO>> getUserByResource(@Valid @RequestBody QueryParamDTO queryParamDto) {
        AbQueryFilter queryFilter = new DefaultAbQueryFilter(queryParamDto);
        PageListDTO<ResourceUserVO> sysResourceList = orgUserManager.getUserByResource(queryFilter);
        return success(sysResourceList);
    }

    /**
     * 过邮箱找回密码方法:发送验证码
     *
     * @param sendCaptchaEmailDTO 发送验证码DTO
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "sendCaptchaEmail")
    public ApiResponse<String> sendCaptchaEmail(@Valid @RequestBody SendCaptchaEmailDTO sendCaptchaEmailDTO) {
        return success(() -> orgUserManager.sendCaptchaEmail(sendCaptchaEmailDTO));
    }

    /**
     * 通过邮箱找回密码方法:重置密码
     *
     * @param setPwdByEmailDTO 设置密码DTO
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "setPwdByEmail")
    public ApiResponse<String> setPwdByEmail(@Valid @RequestBody SetPwdByEmailDTO setPwdByEmailDTO) {
        return success(() -> orgUserManager.setPwdByEmail(setPwdByEmailDTO));
    }

    @Override
    @RequestMapping("listJson")
    public ApiResponse<?> listJson(@Valid @RequestBody QueryParamDTO queryParamDto) {
        PageListDTO<OrgUserListJsonVO> pageList = orgUserManager.queryUserList(new DefaultAbQueryFilter(queryParamDto, getAccessQueryFilters()));
        return ApiResponse.success(pageList);
    }

    @Override
    protected String getEntityDesc() {
        return "用户";
    }
}
