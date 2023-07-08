package com.dstz.auth.core.manager;

import com.dstz.auth.core.entity.ResourceRole;
import com.dstz.auth.rest.model.dto.GrantRoleResourceDTO;
import com.dstz.auth.rest.model.vo.SysResourceTreeVO;
import com.dstz.base.manager.AbBaseManager;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源角色关联表 通用业务类
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
public interface ResourceRoleManager extends AbBaseManager<ResourceRole> {
    /**
     * 根据id查询
     * @param roleId
     */
    List<ResourceRole> getAllByRoleId(String roleId);

    /**
     * 分配角色资源。
     * 
     * @param dto grant dto 
     */
    void grantRoleResource(GrantRoleResourceDTO dto);

    /**
     * 通过url 获取可访问的角色
     * @param url
     * @return
     */
    Set<String> getAccessRoleByUrl(String url);

    /**
     * 根据角色id和编码查询资源树
     * @param roleId
     * @param code
     * @return
     */
    List<SysResourceTreeVO> getRoleResTreeData(String roleId, String code);

    /**
     * 根据系统id和角色id查询资源树
     * @param systemId
     * @param roleId
     * @return
     */
    List<SysResourceTreeVO> getRoleRes(String systemId, String roleId);
}
