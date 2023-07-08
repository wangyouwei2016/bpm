package com.dstz.org.core.manager.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.org.core.constant.OrgStatusCode;
import com.dstz.org.core.entity.Role;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.manager.RoleManager;
import com.dstz.org.core.mapper.RoleMapper;
import com.dstz.org.dto.RemoveCheckRelationDTO;
import com.dstz.org.dto.SaveRoleDTO;
import com.dstz.org.vo.ResourceRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 角色管理 通用服务实现类
 *
 * @author xz
 * @since 2022-02-07
 */
@Service("roleManager")
public class RoleManagerImpl extends AbBaseManagerImpl<Role> implements RoleManager {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    OrgRelationManager orgRelationManager;


    /**
     * 保存角色
     *
     * @param saveRoleDTO 保存角色DTO
     */
    @Override
    public String saveRoleDto(SaveRoleDTO saveRoleDTO) {
        String desc;
        Role role = BeanCopierUtils.transformBean(saveRoleDTO, Role.class);
        //校验角色是否已存在
        Assert.isFalse(isRoleExist(role),
                () -> new BusinessMessage(OrgStatusCode.ROLE_CODE_IS_EXIST));
        if (StrUtil.isEmpty(role.getId())) {
            roleMapper.insert(role);
            desc = "添加%s成功";
        } else {
            roleMapper.updateById(role);
            desc = "更新%s成功";
        }
        return desc;
    }

    @Override
    public boolean isRoleExist(Role role) {
        return !Objects.equals(roleMapper.isRoleExist(role), NumberPool.INTEGER_ZERO);
    }

    /**
     * 批量删除角色
     *
     * @param ids 实体ID集
     * @return 删除的数量
     */
    @Override
    public int removeByIds(Collection<? extends Serializable> ids) {
        for (Serializable id : ids) {
            Assert.isFalse(StrUtil.isEmptyIfStr(id), () -> new BusinessMessage(OrgStatusCode.DEL_FAILED_PARAM_IS_EMPTY));
            orgRelationManager.beforeRemoveRelCheck(new RemoveCheckRelationDTO(null, id.toString()));
        }
        return super.removeByIds(ids);
    }

    /**
     * 通过角色编码查找角色
     * 相对岗位接口实现    GroupRelationApiImpl
     *
     * @param code 角色编码
     * @return 角色
     */
    @Override
    public Role getByCode(String code) {
        return selectOne(Wrappers.lambdaQuery(Role.class).eq(Role::getCode, code));
    }

    /**
     * 通过用户ID 获取角色
     * 组业务接口适配  AbGroupApiImpl
     *
     * @param userId 用户id
     * @return 角色集合
     */
    @Override
    public List<Role> getByUserId(String userId) {
        return roleMapper.getByUserId(userId);
    }

    /**
     * 角色编码集查询角色列表
     * 组业务接口适配  AbGroupApiImpl
     *
     * @param codes 角色编码
     * @return 角色集合
     */
    @Override
    public List<Role> selectByCodes(Collection<String> codes) {
        return roleMapper.selectList(Wrappers.lambdaQuery(Role.class).in(Role::getCode, codes));
    }

    /**
     * 获取资源上的角色列表
     *
     * @param resourcesId 资源id
     * @return 资源角色集合
     */
    @Override
    public List<ResourceRoleVO> getRoleListByResource(String resourcesId) {
        return roleMapper.getRoleListByResource(resourcesId);
    }
}
