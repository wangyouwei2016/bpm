package com.dstz.sys.core.manager;

import com.dstz.base.manager.AbBaseManager;
import com.dstz.sys.api.constant.RightsObjectConstants;
import com.dstz.sys.core.entity.SysAuthorization;
import com.dstz.sys.rest.model.dto.AuthorizationDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 通用资源授权配置 通用业务类
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
public interface SysAuthorizationManager extends AbBaseManager<SysAuthorization> {
    Set<String> getUserRights(String userId);

    /**
     * 获取授权sql
     *
     * @param userId    用户id
     * @param targetKey 默认为Id_ 关联authorization的 targetId在数据库中的字段名字，
     * @return
     */
    Map<String, Object> getUserRightsSql(RightsObjectConstants rightsObject, String userId, String targetKey);

    List<SysAuthorization> getByTarget(RightsObjectConstants rightsObject, String rightsTarget);

    void createAll(AuthorizationDTO saveDto);

    void deleteAuthorizationByRights(RightsObjectConstants rightsObject, String rightsTarget);
}
