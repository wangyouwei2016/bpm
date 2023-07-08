package com.dstz.sys.api;

import com.dstz.sys.api.constant.RightsObjectConstants;
import com.dstz.sys.api.dto.SysAuthorizationDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysAuthorizationApi {

    Set<String> getUserRights(String userId);

    /**
     * 获取授权sql
     *
     * @param userId    用户id
     * @param targetKey 默认为Id_ 关联authorization的 targetId在数据库中的字段名字，
     * @return
     */
    Map<String, Object> getUserRightsSql(RightsObjectConstants rightsObject, String userId, String targetKey);


    /**
     * 通过业务获取授权集合
     *
     * @param rightsObject    业务类型
     * @param rightsCode     业务ID
     * @return
     */
    List<SysAuthorizationDTO> getAuthorizationByRights(RightsObjectConstants rightsObject, String rightsCode);

    /**
     * 通过业务类型和业务ID删除授权
     * @param document
     * @param id
     */
    void deleteAuthorizationByRights(RightsObjectConstants document, String id);
}
