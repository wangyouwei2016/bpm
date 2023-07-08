package com.dstz.auth.core.mapper;

import com.dstz.auth.core.entity.SysResource;
import com.dstz.base.mapper.AbBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统权限资源定义 Mapper 接口
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
@Mapper
public interface SysResourceMapper extends AbBaseMapper<SysResource> {
    /**
     * 根据子系统ID取定义对象。
     *
     * @param appId
     * @return
     */
    List<SysResource> getBySystemId(@Param("appId")String appId);

    /**
     * 根据角色和系统id获取资源。
     *
     * @param appId
     * @param roleId
     * @return
     */
    List<SysResource> getBySystemAndRole(@Param("appId") String appId, @Param("roleId") String roleId);

    /**
     * 判断资源是否存在。
     *
     * @param resource
     * @return
     */
    Integer isExist(SysResource resource);

    /**
     * 根据父ID获取下级节点。
     *
     * @param parentId
     * @return
     */
    List<SysResource> getByParentId(String parentId);

    /**
     * 根据系统id和用户id获取资源列表。
     *
     * @param appId   系统id
     * @param roleIds 角色ID集合
     * @return 系统资源
     */
    List<SysResource> getByAppIdAndRoleIds(@Param("appId") String appId, @Param("roleIds") Iterable<String> roleIds);
}
