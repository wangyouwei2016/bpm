package com.dstz.auth.core.mapper;

import com.dstz.auth.core.entity.ResourceRole;
import com.dstz.base.mapper.AbBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源角色关联表 Mapper 接口
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
@Mapper
public interface ResourceRoleMapper extends AbBaseMapper<ResourceRole> {
    /**
     * 根据roleId获取资源关联信息
     * @param roleId
     * @return
     */
    List<ResourceRole> getByRoleId(String roleId);

    /**
     * 根据roleId和appId移除资源关联
     * @param roleId
     * @param appId
     */
    void removeByRoleAndSystem(@Param("roleId")String roleId, @Param("appId")String appId);

    /**
     * 获取资源和角色的映射关系
     * @return
     */
    List<ResourceRole> getAllResRole();

    /**
     * 根据资源URL获取角色ID
     *
     * @param url 资源URL
     * @return 角色ID集合
     */
    Set<String> getRoleIdByResourceUrl(String url);

    /**
     * 根据角色ID查询应用ID集合
     *
     * @param roleIds 角色ID
     * @return 应用ID集合
     */
    Set<String> selectAppIdByRoleIds(@Param("roleIds") Iterable<String> roleIds);
}
