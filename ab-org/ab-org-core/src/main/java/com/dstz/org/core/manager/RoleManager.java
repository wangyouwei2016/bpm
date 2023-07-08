package com.dstz.org.core.manager;

import com.dstz.base.manager.AbBaseManager;
import com.dstz.org.core.entity.Role;
import com.dstz.org.dto.SaveRoleDTO;
import com.dstz.org.vo.ResourceRoleVO;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色管理 通用业务类
 * </p>
 *
 * @author xz
 * @since 2022-02-07
 */
public interface RoleManager extends AbBaseManager<Role> {

    String saveRoleDto(SaveRoleDTO saveRoleDTO);

    /**
     * 判断角色是否存在。
     *
     * @param role
     * @return
     */
    boolean isRoleExist(Role role);

    /**
     * 根据code获取角色
     *
     * @param code 角色编码
     * @return 角色
     */
    Role getByCode(String code);

    /**
     * 用过用户ID 获取角色
     *
     * @param userId 用户id
     * @return 角色集合
     */
    List<Role> getByUserId(String userId);

    /**
     * 角色编码集查询角色列表
     *
     * @param codes 编码集合
     * @return 角色集合
     */
    List<Role> selectByCodes(Collection<String> codes);

    /**
     * 根据资源获取角色集合
     *
     * @param resourcesId 资源id
     * @return 角色集合
     */
    List<ResourceRoleVO> getRoleListByResource(String resourcesId);

}
