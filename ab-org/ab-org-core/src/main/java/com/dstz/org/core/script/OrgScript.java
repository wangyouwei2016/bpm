package com.dstz.org.core.script;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.enums.IdentityType;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.identityconvert.SysIdentity;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.groovy.script.api.IScript;
import com.dstz.org.api.AbGroupApiImpl;
import com.dstz.org.api.AbUserApiImpl;
import com.dstz.org.api.enums.GroupType;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import com.dstz.org.core.entity.Group;
import com.dstz.org.core.entity.Role;
import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.core.manager.RoleManager;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * 描述：常用org的脚本
 * 作者:aschs
 * 邮箱:aschs@agilebpm.cn
 * 日期:2019年5月26日
 * 版权: 深圳市大世同舟信息科技有限公司
 * </pre>
 */
@Component
public class OrgScript implements IScript {
    protected Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    GroupManager groupMananger;

    @Autowired
    RoleManager roleManager;

    @Autowired
    AbGroupApiImpl abGroupApi;


    @Autowired
    AbUserApiImpl abUserApi;

    /**
     * 检查某用户是否拥有某角色
     *
     * @param userId   用户ID
     * @param roleCode 角色CODE
     * @return
     */
    public boolean checkUserHasRole(String userId, String roleCode) {
        if (StrUtil.isEmpty(userId)) {
            userId = UserContextUtils.getUserId();
        }

        List<? extends IGroup> roleList = abGroupApi.getByGroupTypeAndUserId(GroupType.ROLE.getType(), userId);
        for (IGroup group : roleList) {
            if (group.getGroupCode().equals(roleCode)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取角色的Level
     *
     * @param userId
     * @return
     */
    public Integer getUserRoleLevel(String userId) {
        if (StrUtil.isEmpty(userId)) {
            userId = UserContextUtils.getUserId();
        }
        Integer level = -1;
        List<? extends IGroup> roleList = abGroupApi.getByGroupTypeAndUserId(GroupType.ROLE.getType(), userId);
        for (IGroup group : roleList) {
            if (group.getGroupLevel() != null && group.getGroupLevel() > level) {
                level = group.getGroupLevel();
            }
        }

        return level;
    }

    public Integer getOrgLevel(String orgId) {
        if (StrUtil.isEmpty(orgId)) {
            orgId = UserContextUtils.getGroupId();
        }

        IGroup org = abGroupApi.getByGroupId(GroupType.ORG.getType(), orgId);
        if (org == null)
            return -1;

        return org.getGroupLevel();
    }

    public List<String> getOrgChildrend() {
        String path = UserContextUtils.getGroup().get().getAttrValue("path", String.class);
        if (StrUtil.isEmpty(path)) return Collections.emptyList();

        List<Group> groups = groupMananger.getChildByPathAndType(path, null);
        List<String> groupIds = groups.stream().map(Group::getId).collect(Collectors.toList());
        return groupIds;
    }

    /**
     * <pre>
     * 获取某个岗位下的某些角色的人员列表
     * </pre>
     *
     * @param groupIds  组织id，多个以“,”分隔；为空，则取当前用户所在主组织
     * @param roleCodes 角色编码，多个以“,”分隔
     * @return
     */
    public Set<SysIdentity> getPostUserByGroupAndRole(String groupIds, String roleCodes) {
        if (StrUtil.isEmpty(groupIds)) {
            if (!UserContextUtils.getGroup().isPresent()) {
                throw new BusinessMessage(GlobalApiCodes.PARAMETER_INVALID.formatDefaultMessage("请为当前人员分配组织"));
            }
            groupIds = UserContextUtils.getGroupId();
        }
        List<String> roleIdList = ListUtil.toList(IterUtil.trans(abGroupApi.getByGroupCodes(GroupType.ROLE.getType(), StrUtil.split(roleCodes, StrPool.COMMA)), IGroup::getGroupId));
        List<String> postIds = new ArrayList<>();
        for (String groupId : groupIds.split(StrPool.COMMA)) {
            CollUtil.forEach(roleIdList, (roleId, index) -> postIds.add(String.format(StrPool.FORMATSTR, groupId, roleId)));
        }
        return Sets.newHashSet(IterUtil.trans(abUserApi.getByGroupTypeAndGroupIds(GroupType.POST.getType(), postIds), SysIdentity::new));
    }

    /**
     * <pre>
     * 获取某个组织下的某些角色的人员列表
     * </pre>
     *
     * @param groupIds  组织id，多个以“,”分隔；为空，则取当前用户所在主组织
     * @param roleCodes 角色编码，多个以“,”分隔
     * @return
     */
    public Set<SysIdentity> getSisByGroupAndRole(String groupIds, String roleCodes) {
        Set<SysIdentity> identities = new HashSet<>();
        if (StrUtil.isEmpty(groupIds)) {
            if (!UserContextUtils.getGroup().isPresent()) {
                throw new BusinessMessage(GlobalApiCodes.PARAMETER_INVALID.formatDefaultMessage("请为当前人员分配组织"));
            }
            groupIds = UserContextUtils.getGroupId();
        }
        for (String gi : groupIds.split(StrPool.COMMA)) {
            List<? extends IUser> users = CollUtil.newArrayList(
                    abUserApi.getByGroupTypeAndGroupIds(GroupType.ORG.getType(), Collections.singleton(gi)));
            users.forEach(user -> {
                List<? extends IGroup> roles = abGroupApi.getByGroupTypeAndUserId(GroupType.ROLE.getType(), user.getUserId());
                roles.forEach(role -> {
                    if (Arrays.asList(roleCodes.split(StrPool.COMMA)).contains(role.getGroupCode())) {
                        SysIdentity identity = new SysIdentity(user.getUserId(), user.getFullName(), IdentityType.USER.getKey());
                        identities.add(identity);
                    }
                });
            });
        }

        return identities;
    }

    /**
     * 获取指定组织上级的人员
     *
     * @param groupId
     * @param level
     * @return
     */
    public IGroup getSpecificSuperOrg(String groupId, int level) {
        if (StrUtil.isEmpty(groupId)) {
            groupId = UserContextUtils.getGroupId();
        }

        IGroup group = abGroupApi.getByGroupId(GroupType.ORG.getType(), groupId);
        if (group != null && group.getGroupLevel() != null && level == group.getGroupLevel()) {
            return group;
        }

        return getParentByType(group, level);
    }

    private IGroup getParentByType(IGroup group, int type) {
        if (group == null)
            return null;

        IGroup parentGroup = abGroupApi.getByGroupId(GroupType.ORG.getType(), group.getParentId());
        if (parentGroup != null && parentGroup.getGroupLevel() == type) {
            return parentGroup;
        }
        return getParentByType(parentGroup, type);
    }

    public Set<SysIdentity> getSpecificSuperOrgIdentity(String groupId, int level) {
        IGroup group = this.getSpecificSuperOrg(groupId, level);
        if (group == null)
            return Collections.emptySet();

        Set<SysIdentity> identities = new HashSet<>();
        SysIdentity identity = new SysIdentity(group.getGroupId(), group.getGroupName(), IdentityType.ORG.getKey());
        identities.add(identity);
        return identities;
    }

    /**
     * 获取上级组织作为候选人
     *
     * @param groupId
     * @return
     */
    public Set<SysIdentity> getSuperOrgIdentity(String groupId) {
        if (StrUtil.isEmpty(groupId)) {
            groupId = UserContextUtils.getGroupId();
        }
        Set<SysIdentity> identities = new HashSet<>();
        IGroup group = abGroupApi.getByGroupId(GroupType.ORG.getType(), groupId);
        if (group == null) {
            return identities;
        }
        IGroup parent = abGroupApi.getByGroupId(GroupType.ORG.getType(), group.getParentId());
        if (parent == null) {
            return identities;
        }

        SysIdentity parentIdentity = new SysIdentity(parent.getGroupId(), parent.getGroupName(), IdentityType.ORG.getKey());
        identities.add(parentIdentity);
        return identities;
    }

    // 设置通过ID Name 构建候选人
    public Set<SysIdentity> constructeIdentityUser(String id, String name) {
        if (StrUtil.isEmpty(id) || StrUtil.isEmpty(name)) {
            LOG.warn("表单取候选人失败，表单字段为空 ： constructeIdentityUser id:{},name:{} ", id, name);
            return Collections.emptySet();
        }

        Set<SysIdentity> identities = new HashSet<>();
        SysIdentity identity = new SysIdentity(id, name, IdentityType.USER.getKey());
        identities.add(identity);
        return identities;
    }

    // 设置通过ID Name type 构建Identity
    public Set<SysIdentity> constructeIdentity(String id, String name, String type) {
        Set<SysIdentity> identities = constructeIdentityUser(id, name);
        identities.forEach(identity -> {
            identity.setType(type);
        });
        return identities;
    }


    public Set<SysIdentity> getSpecificSuperPostIdentity(String groupId, int level, String roleCodes) {
        IGroup group = this.getSpecificSuperOrg(groupId, level);
        if (group == null) return Collections.emptySet();

        List<? extends IUser> users = CollUtil.newArrayList(
                abUserApi.getByGroupTypeAndGroupIds(
                        GroupType.ORG.getType(), Collections.singleton(group.getGroupId())
                )
        );
        Set<SysIdentity> identities = new HashSet<>();
        users.forEach(user -> {
            List<? extends IGroup> roles = abGroupApi.getByGroupTypeAndUserId(GroupType.ROLE.getType(), user.getUserId());
            roles.forEach(role -> {
                if (roleCodes.contains(role.getGroupCode())) {
                    SysIdentity identity = new SysIdentity(user.getUserId(), user.getFullName(), IdentityType.USER.getKey());
                    identities.add(identity);
                }
            });
        });
        return identities;
    }


    /**
     * <pre>
     * 根据角色条件获取角色候选组
     * eg:
     * 获取级别大于等于50 小于等于60：
     * orgScript.getRoleSis("level_ >= 50 and level_ <= 60")
     * </pre>
     *
     * @param where
     * @return
     */
    public Set<SysIdentity> getRoleSis(String where) {
        AbQueryFilter filter = DefaultAbQueryFilter.build();
        filter.addParam("defaultWhere", where);
        List<Role> roles = roleManager.query(filter);
        Set<SysIdentity> roleSis = new HashSet<>();
        roles.forEach(role -> {
            SysIdentity identity = new SysIdentity(role.getId(), role.getName(), IdentityType.ROLE.getKey());
            roleSis.add(identity);
        });

        return roleSis;
    }

    /**
     * <pre>
     * 根据id获取组信息
     * </pre>
     *
     * @param type 枚举在：SysIdentity
     * @param ids
     * @return
     */
    public Set<SysIdentity> getSis(String type, String ids) {
        Set<SysIdentity> identities = new HashSet<>();
        if (ids == null) {
            return identities;
        }
        if (IdentityType.USER.getKey().equals(type)) {// 用户
            for (String id : ids.split(StrPool.COMMA)) {
                IUser user = abUserApi.getByUserId(id);
                if (user != null) {
                    SysIdentity identity = new SysIdentity(user.getUserId(), user.getFullName(), IdentityType.USER.getKey());
                    identities.add(identity);
                }
            }
        } else {// 组织
            for (String id : ids.split(StrPool.COMMA)) {
                IGroup group = abGroupApi.getByGroupId(type, id);
                if (group != null) {
                    SysIdentity identity = new SysIdentity(group.getGroupId(), group.getGroupName(), type);
                    identities.add(identity);
                }
            }
        }

        return identities;
    }


}
