package com.dstz.org.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.org.api.enums.GroupGradeConstant;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.core.entity.Group;
import com.dstz.org.core.entity.Role;
import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.core.manager.RoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 相对岗位接口实现
 *
 * @author wacxhs
 */
@Component
public class GroupRelationApiImpl implements GroupRelationApi {

	private static final Logger LOG = LoggerFactory.getLogger(GroupRelationApiImpl.class);

	private final GroupManager groupManager;
	private final RoleManager roleManager;

	public GroupRelationApiImpl(GroupManager groupManager, RoleManager roleManager) {
		this.groupManager = groupManager;
		this.roleManager = roleManager;
	}

	@Override
	public List<? extends IGroup> getPostByGroupAndRoles(String orgIds, String roleKeys) {
		LOG.debug("通过组织角色查询岗位,组织IDs：{},角色KEYs:{}", orgIds, roleKeys);
		if (StrUtil.isEmpty(orgIds) || StrUtil.isEmpty(roleKeys)) {
			return Collections.emptyList();
		}

		List<Group> groups = groupManager.selectByIds(StrUtil.split(orgIds, StrPool.COMMA));

		return getPostsByGroupsAndRoleKeys(groups, roleKeys);
	}

	/**
	 * 通过组 角色构造 岗位
	 * 如果角色为空，则返回组织
	 *
	 * @param groups
	 * @param roleKeys
	 * @return
	 */
	private List<IGroup> getPostsByGroupsAndRoleKeys(List<Group> groups, String roleKeys) {

		if (CollectionUtil.isEmpty(groups)) {
			return Collections.emptyList();
		}

		// 如果角色未指定则直接返回指定的组织即可
		if (StrUtil.isEmpty(roleKeys)) {
			return CollUtil.map(groups, AbOrg::new, false);
		}

		String[] roleKeyArr = roleKeys.split(StrUtil.COMMA);

		List<IGroup> postList = new ArrayList<>();
		for (String roleKey : roleKeyArr) {
			Role role = roleManager.getById(roleKey);
			if (role == null) {
				continue;
			}

			for (Group g : groups) {
				postList.add(AbRole.newPost(g, role));
			}
		}


		if (LOG.isDebugEnabled()) {
			LOG.debug("通过组织角色查询岗位返回结果{}条为：{}", postList.size(), JsonUtils.toJSONString(postList));
		}

		return postList;
	}

	@Override
	public List<? extends IGroup> getPostByGroupParentAndRoles(String orgIds, String roleKeys) {
		LOG.debug("通过组织上级,角色查询岗位,组织IDs：{},角色KEYs:{}", orgIds, roleKeys);
		if (StrUtil.isEmpty(orgIds) || StrUtil.isEmpty(roleKeys)){
			return Collections.emptyList();
		}

		List<Group> groups = groupManager.selectByIds(StrUtil.split(orgIds, StrUtil.COMMA));

		List<String> parentsGroupsIds = groups.stream().filter(group -> !StrPool.NUMBER_ZERO.equals(group.getParentId()))
		                                      .map(Group::getParentId).collect(Collectors.toList());
		if (parentsGroupsIds.isEmpty()) {
			List<String> groupNames = groups.stream().map(Group::getName).collect(Collectors.toList());
			LOG.warn("相对岗位人员查询失败,父组织不存在 ：" + groupNames);
			return Collections.emptyList();
		}
		// 父groups
		List<Group> parentGroups = groupManager.selectByIds(parentsGroupsIds);

		return getPostsByGroupsAndRoleKeys(parentGroups, roleKeys);
	}

	@Override
	public List<? extends IGroup> getPostByGroupSpecicalLevelParentAndRoles(String orgIds, Integer parentOrgSpecicalLevel, String roleKeys) {
		LOG.debug("通过指定级别的组织上级,角色查询岗位,组织IDs：{},角色KEYs:{},级别：{}", orgIds, roleKeys, parentOrgSpecicalLevel);
		if (StrUtil.isEmpty(orgIds) || StrUtil.isEmpty(roleKeys) || parentOrgSpecicalLevel == null || parentOrgSpecicalLevel < 1) {
			return Collections.emptyList();
		}

		List<Group> groups = groupManager.selectByIds(StrUtil.split(orgIds, StrPool.COMMA));

		List<String> upLevelParent = new ArrayList<>(groups.size());
		for (Group group : groups) {
			String path = group.getPath();
			if (StrUtil.isEmpty(path)) {
				continue;
			}
			
			List<String> parentIds = StrUtil.split(path, StrPool.DOT);
			if (parentIds.size() < (parentOrgSpecicalLevel + 1)) {
				LOG.debug("组织{}上{}级的组织不存在", group.getName(), parentOrgSpecicalLevel);
				continue;
			}

			String specicalLevelId = parentIds.get(parentIds.size() - parentOrgSpecicalLevel - 1);
			upLevelParent.add(specicalLevelId);
		}

		List<Group> parentLevelGrouops = groupManager.selectByIds(upLevelParent);

		return getPostsByGroupsAndRoleKeys(parentLevelGrouops, roleKeys);
	}

	@Override
	public List<? extends IGroup> getPostByGroupSpecicalTypeParentAndRoles(String orgIds, Integer parentOrgFilterType, String roleKeys) {
		LOG.debug("通过指定级别的组织上级,角色查询岗位,组织IDs：{},角色KEYs:{},级别：{}", orgIds, roleKeys, parentOrgFilterType);
		if (StrUtil.isEmpty(orgIds) || StrUtil.isEmpty(roleKeys) || parentOrgFilterType == null) {
			return new ArrayList<>();
		}

		List<Group> groups = groupManager.selectByIds(StrUtil.split(orgIds, StrUtil.COMMA));

		List<Group> parentGroups = new ArrayList<>();
		for (Group group : groups) {

			if (parentOrgFilterType.equals(Integer.valueOf(group.getType()))) {
				parentGroups.add(group);
				continue;
			}

			Group parentTypeGroup = getParentByType(group, parentOrgFilterType);
			if (parentTypeGroup == null) {
				GroupGradeConstant type = GroupGradeConstant.valueOfKey(parentOrgFilterType);
				LOG.debug("组织{}上级类型为【{}】的组织不存在", group.getName(), type == null ? "未知" : type.name());
				continue;
			}
			parentGroups.add(parentTypeGroup);
		}

		return getPostsByGroupsAndRoleKeys(parentGroups, roleKeys);
	}


	private Group getParentByType(Group group, Integer type) {
		if (group == null || type == null) {
			return null;
		}

		Group parentGroup = groupManager.getById(group.getParentId());
		if (parentGroup != null && type.equals(Integer.valueOf(parentGroup.getType()))) {
			return parentGroup;
		}
		return getParentByType(parentGroup, type);
	}

	@Override
	public List<? extends IGroup> getPostByGroupChildAndRoles(String orgIds, String roleKeys) {
		LOG.debug("通过组织上级,角色查询岗位,组织IDs：{},角色KEYs:{}", orgIds, roleKeys);
		if (StrUtil.isEmpty(orgIds) || StrUtil.isEmpty(roleKeys)) {
			return Collections.emptyList();
		}

		List<Group> children = new ArrayList<>();

		String[] groupIds = orgIds.split(StrUtil.COMMA);
		for (String id : groupIds) {
			children.addAll(groupManager.getChildrenByParentId(id));
		}

		return getPostsByGroupsAndRoleKeys(children, roleKeys);
	}

	@Override
	public List<? extends IGroup> getPostByGroupSpecicalTypeChildAndRoles(String orgIds, Integer childOrgFilterType, String roleKeys) {
		LOG.debug("通过指定级别的组织上级,角色查询岗位,组织IDs：{},角色KEYs:{},级别：{}", orgIds, roleKeys, childOrgFilterType);
		if (StrUtil.isEmpty(orgIds) || StrUtil.isEmpty(roleKeys) || childOrgFilterType == null) {
			return Collections.emptyList();
		}

		List<Group> groups = groupManager.selectByIds(StrUtil.split(orgIds, StrPool.COMMA));

		List<Group> parentGroups = new ArrayList<>();
		for (Group group : groups) {
			if (NumberUtil.isNumber(group.getType()) && Integer.valueOf(group.getType()).equals(childOrgFilterType)) {
				parentGroups.add(group);
			}

			List<Group> childTypeGroup = getChildByType(group, childOrgFilterType);
			if (CollectionUtil.isEmpty(childTypeGroup)) {
				GroupGradeConstant type = GroupGradeConstant.valueOfKey(childOrgFilterType);
				LOG.debug("组织{}下级类型为【{}】的组织不存在", group.getName(), type == null ? "未知" : type.name());
				continue;
			}
			parentGroups.addAll(childTypeGroup);
		}

		return getPostsByGroupsAndRoleKeys(parentGroups.stream().collect(
				Collectors.collectingAndThen(
						Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Group::getId))), ArrayList::new)), roleKeys);
	}

	private List<Group> getChildByType(Group group, Integer type) {
		if (group == null || type == null) {
			return null;
		}
		return groupManager.getChildByPathAndType(group.getPath(), type);
	}
}
