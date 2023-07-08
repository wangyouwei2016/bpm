package com.dstz.sys.core.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.org.api.GroupApi;
import com.dstz.org.api.model.IGroup;
import com.dstz.sys.api.constant.RightsObjectConstants;
import com.dstz.sys.core.entity.SysAuthorization;
import com.dstz.sys.core.manager.SysAuthorizationManager;
import com.dstz.sys.core.mapper.SysAuthorizationMapper;
import com.dstz.sys.rest.model.dto.AuthorizationDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通用资源授权配置 通用服务实现类
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@Service("sysAuthorizationManager")
public class SysAuthorizationManagerImpl extends AbBaseManagerImpl<SysAuthorization> implements SysAuthorizationManager {


    private static final String RIGHT_TYPE_USER = "user";
    private static final String RIGHT_TYPE_ALL = "all";

    private final SysAuthorizationMapper sysAuthorizationMapper;
    private final GroupApi groupApi;

    public SysAuthorizationManagerImpl(SysAuthorizationMapper sysAuthorizationMapper, GroupApi groupApi) {
        this.sysAuthorizationMapper = sysAuthorizationMapper;
        this.groupApi = groupApi;
    }

    /**
     * 获取用户的权限
     * type-vale
     */
    @Override
    public Set<String> getUserRights(String userId) {
        List<? extends IGroup> list = groupApi.getByUserId(userId);

        Set<String> rights = new HashSet<String>();
        rights.add(String.format("%s-%s", userId, RIGHT_TYPE_USER));
        rights.add(String.format("%s-%s", RIGHT_TYPE_USER, RIGHT_TYPE_ALL));


        if (CollectionUtil.isEmpty(list)) {
            return rights;
        }

        for (IGroup group : list) {
            rights.add(String.format("%s-%s", group.getGroupId(), group.getGroupType()));
        }

        return rights;
    }


    /**
     * 获取用户的权限sql, sql中需要使用 ${rightsSql},若不使用可以使用 Set rights, 自行拼装
     *
     * @param rightsObject
     * @param userId       用户id
     * @param targetKey    默认为Id_ 关联authorization的 targetId在数据库中的字段名字，
     * @return @rightsSql = inner join sys_authorization rights on id_ = rights.rights_id_ and rights_permission_code_ in ( id-type,groupid-type )
     */
    @Override
    public Map<String, Object> getUserRightsSql(RightsObjectConstants rightsObject, String userId, String targetKey) {
        if (StrUtil.isEmpty(targetKey)) {
            targetKey = "id_";
        }

        StringBuilder sb = new StringBuilder();

        Set<String> rights = getUserRights(userId);

        for (String r : rights) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append("'").append(r).append("'");
        }
        sb.insert(0, "inner join sys_authorization rights on " + targetKey + " = rights.rights_target_  and  rights.rights_object_ ='" + rightsObject.key() + "' and rights_permission_code_ in ( ");
        sb.append(")");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("rightsSql", sb.toString());

        param.put("rights", rights);

        return param;
    }


    @Override
    public List<SysAuthorization> getByTarget(RightsObjectConstants rightsObject, String rightsTarget) {
        return sysAuthorizationMapper.getByTarget(rightsObject.key(), rightsTarget);
    }


    @Override
    public void createAll(AuthorizationDTO saveDto) {
        sysAuthorizationMapper.deleteByTarget(saveDto.getRightsObject().key(), saveDto.getRightsTarget());
        final Date nowTime = new Date();
        final String currentUserId = UserContextUtils.getUserId();
        Iterator<SysAuthorization> sysAuthorizationIterator = saveDto.getAuthorizationList().stream().peek(authorization -> {
            authorization.setRightsPermissionCode(String.format("%s-%s", authorization.getRightsIdentity(), authorization.getRightsType()));
            if (StrUtil.isEmpty(authorization.getRightsObject())) {
                authorization.setRightsObject(saveDto.getRightsObject().key());
            }
            if (StrUtil.isEmpty(authorization.getRightsTarget())) {
                authorization.setRightsTarget(saveDto.getRightsTarget());
            }
            authorization.setRightsCreateBy(currentUserId);
            authorization.setRightsCreateTime(nowTime);
        }).iterator();
        bulkCreate(IterUtil.asIterable(sysAuthorizationIterator));
    }

    @Override
    public void deleteAuthorizationByRights(RightsObjectConstants rightsObject, String rightsTarget) {
        remove(Wrappers.<SysAuthorization>lambdaQuery()
                .eq(SysAuthorization::getRightsObject, rightsObject)
                .eq(SysAuthorization::getRightsTarget, rightsTarget));
    }
}
