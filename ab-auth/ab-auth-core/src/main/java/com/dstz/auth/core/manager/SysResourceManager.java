package com.dstz.auth.core.manager;

import com.dstz.auth.authentication.api.model.ISysResource;
import com.dstz.auth.core.entity.SysResource;
import com.dstz.auth.rest.model.dto.SysResourceDTO;
import com.dstz.auth.rest.model.vo.SysResourceVO;
import com.dstz.base.manager.AbBaseManager;

import java.util.List;

/**
 * <p>
 * 系统权限资源定义 通用业务类
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
public interface SysResourceManager extends AbBaseManager<SysResource> {

    /**
     * 根据子系统ID获取实体列表。
     */
    List<SysResource> getBySystemId(String id);


    /**
     * 根据系统和角色ID获取资源。
     *
     * @param systemId
     * @param roleId
     * @return
     */
    List<SysResource> getBySystemAndRole(String systemId, String roleId);

    /**
     * 判断资源是否存在。
     *
     * @param resource
     * @return
     */
    boolean isExist(SysResource resource);

    /**
     * 根据资源id递归删除资源数据。
     *
     * @param resId
     */
    void removeByResId(String resId);

    /**
     * 根据系统id和用户id获取资源。
     *
     * @param appId  应用ID
     * @param userId 用户ID
     * @return 资源列表
     */
    List<SysResource> getByAppIdAndUserId(String appId, String userId);

    /**
     * 根据id获取资源明细信息
     *
     * @param id
     * @param systemId
     * @param parentId
     * @return
     */
    SysResourceVO getResourceDetailById(String id, String parentId, String systemId);


    /**
     * 保存树结构
     * @param sysResource
     */
    void saveTree(SysResourceDTO sysResource);

    /**
     * 保存单条资源
     * @param sysResource
     */
    String saveSysResource(SysResource sysResource);

    /**
     * 通过资源code集合删除资源对象
     * @param  codeList  资源code集合
     */
    void deleteResourceByCode(List<String> codeList);
}
