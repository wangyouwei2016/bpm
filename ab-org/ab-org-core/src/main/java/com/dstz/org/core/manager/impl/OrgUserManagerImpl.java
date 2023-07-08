package com.dstz.org.core.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.auth.utils.VerifyCodeUtil;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.common.cache.ICache;
import com.dstz.base.common.constats.AbCacheRegionConstant;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.encrypt.EncryptUtil;
import com.dstz.base.common.enums.EnvironmentConstants;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.enums.IdentityType;
import com.dstz.base.common.events.AbUserEvent;
import com.dstz.base.common.exceptions.ApiException;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.identityconvert.SysIdentity;
import com.dstz.base.common.property.PropertyEnum;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.component.msg.api.MsgApi;
import com.dstz.component.msg.api.dto.MsgDTO;
import com.dstz.org.core.constant.OrgStatusCode;
import com.dstz.org.core.entity.OrgRelation;
import com.dstz.org.core.entity.OrgUser;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.manager.OrgUserManager;
import com.dstz.org.core.mapper.OrgUserMapper;
import com.dstz.org.dto.*;
import com.dstz.org.enums.RelationTypeConstant;
import com.dstz.org.vo.OrgUserInfoVO;
import com.dstz.org.vo.OrgUserListJsonVO;
import com.dstz.org.vo.OrgUserVO;
import com.dstz.org.vo.ResourceUserVO;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 用户表 通用服务实现类
 *
 * @author xz
 * @since 2022-02-07
 */
@Service("userManager")
public class OrgUserManagerImpl extends AbBaseManagerImpl<OrgUser> implements OrgUserManager {

    @Autowired
    OrgUserMapper orgUserMapper;
    @Autowired
    OrgRelationManager orgRelationManager;
    @Autowired
    ICache cache;
    @Autowired
    private MsgApi msgApi;


    /**
     *判断系统中用户是否存在
     * @param user  用户
     * @return  Boolean
     */
    @Override
    public boolean isUserExist(OrgUser user) {
        return orgUserMapper.isUserExist(user) > NumberPool.INTEGER_ZERO;
    }

    /**
     * 保存用户方法
     *
     * @param saveOrgUserDTO 保存用户DTO
     */
    @Override
    public void saveUserDto(SaveOrgUserDTO saveOrgUserDTO) {
        OrgUser orgUser = BeanCopierUtils.transformBean(saveOrgUserDTO, OrgUser.class);
        Assert.notNull(orgUser, () -> new BusinessMessage(OrgStatusCode.INPUT_INFORMATION_IS_EMPTY));
        //查询账户是否已存在账户
        Assert.isFalse(
                isUserExist(orgUser),
                () -> new BusinessMessage(OrgStatusCode.ACCOUNT_IS_EXIST));

        //新增用户
        if (StrUtil.isEmpty(orgUser.getId())) {
            if (StrUtil.isEmpty(orgUser.getPassword())){
                orgUser.setPassword(StrPool.STRING_PWD_ONZ);
            }
            //密码进行加密
            orgUser.setPassword(EncryptUtil.encryptSha256(orgUser.getPassword()));
            orgUserMapper.insert(orgUser);
        }else {
            orgUserMapper.updateById(orgUser);
            //删除旧的关联关系
            orgRelationManager.removeByUserId(orgUser.getId());
        }

        //获取新的关联关系
        List<OrgRelationDTO> orgRelationList = saveOrgUserDTO.getOrgRelationList();
        if (CollectionUtil.isEmpty(orgRelationList)) return;

        //设置绑定的用户id
        orgRelationList.forEach(rel -> rel.setUserId(orgUser.getId()));
        //批量新增关联关系
        orgRelationManager.bulkCreate(BeanCopierUtils.transformList(orgRelationList, OrgRelation.class));
    }

    /**
     * 获取个人中心信息
     *
     * @return 用户信息VO
     */
    @Override
    public OrgUserInfoVO getUserInfo() {
        OrgUserInfoVO orgUserInfoVO = null;
        OrgUser user=getById(UserContextUtils.getUserId());
        if(user!= null) {
            orgUserInfoVO=BeanCopierUtils.transformBean(getById(UserContextUtils.getUserId()), OrgUserInfoVO.class);
            orgUserInfoVO.setOrgRelationList(orgRelationManager.getUserRelation(user.getId(), null));
        }
        return orgUserInfoVO;
    }

    /**
     * 保存个人信息
     * @param saveOrgUserInfoDTO-保存个人信息DTO
     */
    @Override
    public void saveUserInfo(SaveOrgUserInfoDTO saveOrgUserInfoDTO) {
        OrgUser orgUser = BeanCopierUtils.transformBean(saveOrgUserInfoDTO, OrgUser.class);
        Assert.notNull(orgUser, () -> new BusinessMessage(OrgStatusCode.INPUT_INFORMATION_IS_EMPTY));
        orgUserMapper.updateById(orgUser);
    }

    /**
     * 获取用户信息
     *
     * @param id 用户id
     * @return 用户信息VO
     */
    public OrgUserVO getUserVO(String id) {
        OrgUserVO orgUserVO = BeanCopierUtils.transformBean(getById(id), OrgUserVO.class);
        Assert.notNull(orgUserVO, () -> new BusinessMessage(OrgStatusCode.OPERATION_FAILURE));

        //设置用户关系信息
        orgUserVO.setOrgRelationList(orgRelationManager.getUserRelation(id, null));
        return orgUserVO;
    }

    /**
     * 重置密码为1
     *
     * @param id 用户id
     */
    @Override
    public void resetUserPassword(String id) {
        OrgUser user = getById(id);
        //设置加密后的密码
        user.setPassword("a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=");
        //清空账户到期时间
        user.setExpireDate(null);

        orgUserMapper.updateById(user);
    }

    /**
     * 修改用户状态
     *
     * @param id 用户id
     */
    @Override
    public void updateUserStatus(String id) {
        OrgUser user = getById(id);
        //设置状态
        user.setStatus(NumberPool.INTEGER_ONE.equals(user.getStatus()) ? NumberPool.INTEGER_ZERO : NumberPool.INTEGER_ONE);
        orgUserMapper.updateById(user);
    }

    /**
     * * 删除用户 需要删除关联关系
     *
     * @param ids 实体ID集
     * @return 删除用户数量
     */
    @Override
    public int removeByIds(Collection<? extends Serializable> ids) {
        orgRelationManager.removeByIds(ids);
        return super.removeByIds(ids);
    }

    /**
     * 修改个人密码
     *
     * @param updateUserPassWorldDTO 更新密码DTO
     */
    @Override
    public void updateUserPassWorld(UpdateUserPassWorldDTO updateUserPassWorldDTO) {
        //获取用户
        OrgUser user;
        String currentUserId = UserContextUtils.getUserId();
        if (StrUtil.isEmpty(currentUserId)) {
            user = selectOne(Wrappers.lambdaQuery(OrgUser.class).eq(OrgUser::getAccount, updateUserPassWorldDTO.getAccount()));
        } else {
            user = getById(currentUserId);
        }

        //正则检验
        checkPassWorld(updateUserPassWorldDTO.getNewPassword());
        //检验原密码密码
        Assert.isTrue(
                EncryptUtil.encryptSha256(updateUserPassWorldDTO.getOldPassword()).equals(user.getPassword()),
                () -> new BusinessMessage(OrgStatusCode.OLD_PWD_INPUT_ERROR));

        user.setPassword(EncryptUtil.encryptSha256(updateUserPassWorldDTO.getNewPassword()));

        //设置到期时间
        user.setExpireDate(DateUtils.addDays(new Date(), PropertyEnum.PWD_LOSE_COUNT.getPropertyValue(int.class)));
        orgUserMapper.updateById(user);
    }

    /**
     * 更新密码的校验
     *
     * @param passWord 密码
     */
    private void checkPassWorld(String passWord) {
        //演示环境不允许修改
        Assert.isFalse(
                SpringUtil.getActiveProfile().equalsIgnoreCase(EnvironmentConstants.DEMO.getKey()),
                () -> new ApiException(OrgStatusCode.FROM_MODIFICATION_DEMO_PWD));

        //正则校验密码
        Assert.isTrue(
                Pattern.matches(PropertyEnum.PWD_CHECK_RULE_KEY.getPropertyValue(String.class), passWord),
                () -> new BusinessMessage(
                        GlobalApiCodes.PARAMETER_INVALID.formatDefaultMessage(
                                PropertyEnum.PWD_CHECK_RULE_TXT.getPropertyValue(String.class))));
    }

    /**
     * 发送找回密码的验证码
     *
     * @param sendCaptchaEmailDTO 发送验证码DTO
     */
    @Override
    public void sendCaptchaEmail(SendCaptchaEmailDTO sendCaptchaEmailDTO) {
        //根据账户查询用户
        OrgUser user = selectOne(Wrappers.lambdaQuery(OrgUser.class).eq(OrgUser::getAccount, sendCaptchaEmailDTO.getAccount()));
        //检验用户和邮箱
        Assert.notNull(user, () -> new BusinessMessage(OrgStatusCode.USER_DOES_NOT_EXIST));
        Assert.notNull(user.getEmail(), () -> new BusinessMessage(OrgStatusCode.EMAIL_DOES_NOT_EXIST));
        Assert.isTrue(sendCaptchaEmailDTO.getEmail().equals(user.getEmail()), () -> new BusinessMessage(OrgStatusCode.EMAIL_INPUT_ERROR));

        //生成验证码
        final String verifyCode = VerifyCodeUtil.generateVerifyCode();
        List<SysIdentity> receivers = Collections.singletonList(new SysIdentity(user.getId(), user.getAccount(), IdentityType.USER.getKey()));
        List<String> msgTypes = Collections.singletonList("email");
        //缓存中设置验证码的有效期
        cache.put(AbCacheRegionConstant.LOGIN_PWD_REGION, user.getAccount(), verifyCode);
        MsgDTO msgDTO = new MsgDTO("AgileBPM 找回密码验证码", "forgotPasswordValidation", receivers, msgTypes, ImmutableMap.of("verifyCode", verifyCode));
        msgApi.sendMsg(msgDTO);
    }

    /**
     * 通过验证码设置密码
     *
     * @param setPwdByEmailDTO 设置密码DTO
     */
    @Override
    public void setPwdByEmail(SetPwdByEmailDTO setPwdByEmailDTO) {
        OrgUser user = selectOne(Wrappers.lambdaQuery(OrgUser.class).eq(OrgUser::getAccount, setPwdByEmailDTO.getAccount()));
        Assert.notNull(user, () -> new BusinessMessage(OrgStatusCode.USER_DOES_NOT_EXIST));
        //检验是否开启邮箱找回密码功能
        Assert.isFalse(
                PropertyEnum.IS_OPEN_RESET_PWD_BY_EMAIL.getPropertyValue(boolean.class),
                () -> new ApiException(OrgStatusCode.PWD_RESET_FUNCTION_IS_NOT_ENABLED));

        Assert.isTrue(
                setPwdByEmailDTO.getNewPassword().equals(setPwdByEmailDTO.getConfirmPassword()),
                () -> new BusinessMessage(OrgStatusCode.NEW_PWD_IS_DIFFERENT_CONFIRM_PWD));
        //从缓存获取验证码
        Object captcha = cache.getIfPresent(AbCacheRegionConstant.LOGIN_PWD_REGION, setPwdByEmailDTO.getAccount());
        //验证码校验
        Assert.isFalse(
                StrUtil.isEmptyIfStr(captcha) || setPwdByEmailDTO.getCaptcha().equals(captcha),
                () -> new BusinessMessage(OrgStatusCode.VERIFICATION_CODE_IS_EXPIRED));

        //到期时间
        user.setExpireDate(DateUtils.addDays(new Date(), PropertyEnum.PWD_LOSE_COUNT.getPropertyValue(int.class)));
        //密码加密
        user.setPassword(EncryptUtil.encryptSha256(setPwdByEmailDTO.getNewPassword()));
        orgUserMapper.updateById(user);
    }

    /**
     * 通过relId和type查找用户
     *
     * @param relId 岗位id
     * @param type  类型
     * @return OrgUser集合
     */
    @Override
    public List<OrgUser> getUserListByRelation(String relId, String type) {
        if (type.equals(RelationTypeConstant.POST_USER.getKey())) {
            String[] postId = relId.split(StrPool.UNDERLINE);
            if (postId.length != 2) {
                return Collections.emptyList();
            }
            return orgUserMapper.getUserListByPost(postId[1], postId[0]);
        }
        return orgUserMapper.getUserListByRelation(relId, type);
    }

    /**
     * 权限用户查询
     *
     * @param queryFilter 参数过滤
     */
    @Override
    public PageListDTO<ResourceUserVO> getUserByResource(AbQueryFilter queryFilter) {
        return orgUserMapper.getUserByResource(queryFilter);
    }

    /**
     * 仅供内置的自定义对话框-查询用户列表使用
     *
     * @param paramDTO 请求参数
     * @return PageListDTO数据对象
     */
    @Override
    public PageListDTO<OrgUser> queryUser(QueryParamDTO paramDTO) {
        return orgUserMapper.queryUser(new DefaultAbQueryFilter(paramDTO));
    }

    @Override
    public int updateByPrimaryKeySelective(OrgUser record) {
        return orgUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageListDTO<OrgUserListJsonVO> queryUserList(AbQueryFilter abQueryFilter) {
        return orgUserMapper.queryUserList(abQueryFilter);
    }
}
