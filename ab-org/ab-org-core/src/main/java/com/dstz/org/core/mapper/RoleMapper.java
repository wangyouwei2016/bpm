package com.dstz.org.core.mapper;

import com.dstz.base.mapper.AbBaseMapper;
import com.dstz.org.core.entity.Role;
import com.dstz.org.vo.ResourceRoleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色管理 Mapper 接口
 * </p>
 *
 * @author xz
 * @since 2022-02-07
 */
@Mapper
public interface RoleMapper extends AbBaseMapper<Role> {


    /**
     * 根据资源id获取角色列表
     *
     * @param resourcesId 资源id
     * @return 角色集合
     */
    List<ResourceRoleVO> getRoleListByResource(String resourcesId);




    /**
     * 用过用户ID 获取角色
     *
     * @param userId 用户id
     * @return 角色集合
     */
    List<Role> getByUserId(String userId);


    /**
     * 判断角色系统中是否存在。
     *
     * @param role 角色
     * @return  count
     */
    Integer isRoleExist(Role role);
}
