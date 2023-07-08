package com.dstz.org.core.manager;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.manager.AbBaseManager;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.org.core.entity.OrgUser;
import com.dstz.org.dto.*;
import com.dstz.org.vo.OrgUserInfoVO;
import com.dstz.org.vo.OrgUserListJsonVO;
import com.dstz.org.vo.OrgUserVO;
import com.dstz.org.vo.ResourceUserVO;

import java.util.List;

/**
 * <p>
 * 用户表 通用业务类
 * </p>
 *
 * @author xz
 * @since 2022-02-07
 */
public interface OrgUserManager extends AbBaseManager<OrgUser> {

    /**
     * 用户权限查询
     *
     * @param queryFilter 查询过滤器
     * @return 分页用户查询结果
     */
    PageListDTO<ResourceUserVO> getUserByResource(AbQueryFilter queryFilter);

    /**
     * 仅供内置的自定义对话框-查询用户列表使用
     *
     * @param queryParamDTO 查询条件DTO
     * @return 分页角色集合
     */
    PageListDTO<OrgUser> queryUser(QueryParamDTO queryParamDTO);

    /**
     * 获取用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    OrgUserVO getUserVO(String id);

    /**
     * 发送验证码
     *
     * @param sendCaptchaEmailDTO 发送验证码DTO
     */
    void sendCaptchaEmail(SendCaptchaEmailDTO sendCaptchaEmailDTO);

    /**
     * 设置密码
     *
     * @param setPwdByEmailDTO 设置面DTO
     */
    void setPwdByEmail(SetPwdByEmailDTO setPwdByEmailDTO);

    /**
     * 根据角色ID获取用户列表
     *
     * @param relId 岗位id
     * @param type  类型
     * @return 用户集合
     */
    List<OrgUser> getUserListByRelation(String relId, String type);

    /**
     * 重置密码
     *
     * @param id 用户id
     */
    void resetUserPassword(String id);

    /**
     * 更新密码
     *
     * @param updateUserPassWorldDTO 更新密码DTO
     */
    void updateUserPassWorld(UpdateUserPassWorldDTO updateUserPassWorldDTO);

    /**
     * 修改用户状态
     *
     * @param id 用户id
     */
    void updateUserStatus(String id);

    /**
     * 保存用户
     *
     * @param saveOrgUserDTO 保存用户DTO
     */
    void saveUserDto(SaveOrgUserDTO saveOrgUserDTO);

    /**
     * 保存个人信息
     * @param saveOrgUserDTO-保存个人信息DTO
     */
    void saveUserInfo(SaveOrgUserInfoDTO saveOrgUserDTO);

    /**
     * 根据主键选择性更新记录
     *
     * @param record 更新记录
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(OrgUser record);

    /**
     * 个人中心信息
     *
     * @return 个人信息VO
     */
    OrgUserInfoVO getUserInfo();


    /**
     * 判断系统中用户是否存在。
     */
    boolean isUserExist(OrgUser user);

    /**
     * 分页列表查询
     *
     * @param abQueryFilter  查询过滤器
     * @return  OrgUserListJsonVO 集合
     */
    PageListDTO<OrgUserListJsonVO> queryUserList(AbQueryFilter abQueryFilter);

}
