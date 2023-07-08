package com.dstz.org.core.manager.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.BeanConversionUtils;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.common.utils.IdGeneratorUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.org.api.enums.GroupGradeConstant;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.core.constant.OrgStatusCode;
import com.dstz.org.core.entity.Group;
import com.dstz.org.core.entity.OrgRelation;
import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.mapper.GroupMapper;
import com.dstz.org.core.mapper.OrgUserMapper;
import com.dstz.org.dto.RemoveCheckRelationDTO;
import com.dstz.org.dto.SaveGroupDTO;
import com.dstz.org.vo.GroupTreeVO;
import com.dstz.org.vo.GroupUserCountVO;
import com.dstz.org.vo.GroupVO;
import com.dstz.org.vo.OrgRelationUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织架构 通用服务实现类
 *
 * @author xz
 * @since 2022-02-07
 */
@Service("groupManager")
public class GroupManagerImpl extends AbBaseManagerImpl<Group> implements GroupManager {

    @Autowired
    GroupMapper groupMapper;
    @Autowired
    OrgUserMapper orgUserMapper;
    @Autowired
    OrgRelationManager orgRelationManager;


    /**
     * 删除组织接口
     *
     * @param ids 实体ID集
     * @return 返回删除数量
     */
    @Override
    public int removeByIds(Collection<? extends Serializable> ids) {
        Assert.isFalse(CollectionUtil.isEmpty(ids), () -> new BusinessMessage(OrgStatusCode.DEL_FAILED_PARAM_IS_EMPTY));

        for (Serializable id : ids) {
            orgRelationManager.beforeRemoveRelCheck(new RemoveCheckRelationDTO(id.toString(), null));
            Group group = groupMapper.selectById(id);
            Assert.notNull(group, () -> new BusinessMessage(OrgStatusCode.OPERATION_FAILURE));

            if (StrUtil.isNotEmpty(group.getPath())) {
                //查询子组织
                List<Group> childList = groupMapper.getChildByPath(group.getPath() + StrPool.MOD, null);
                if (CollectionUtil.isNotEmpty(childList)) {
                    // 级联删除子组织
                    for (Group child : childList) {
                        orgRelationManager.beforeRemoveRelCheck(new RemoveCheckRelationDTO(child.getId(), null));
                        super.removeById(child.getId());
                    }
                }
            }
        }
        return super.removeByIds(ids);
    }

    /**
     * 获取组织详情接口
     *
     * @param id 组织id
     * @return GroupVO
     */
    @Override
    public GroupVO getGroupVo(String id) {
        GroupVO group = BeanCopierUtils.transformBean(getById(id), GroupVO.class);
        Assert.notNull(group, () -> new BusinessMessage(OrgStatusCode.OPERATION_FAILURE));

        List<OrgRelationUserVO> orgRelationList = orgRelationManager.getGroupPost(group.getId());
        group.setOrgRelationList(orgRelationList);
        if (!StrPool.NUMBER_ZERO.equals(group.getParentId())) {
            group.setParentName(getById(group.getParentId()).getName());
        }
        return group;
    }

    /**
     * 获取组织树接口
     *
     * @return GroupTreeVO
     */
    @Override
    public List<GroupTreeVO> getOrgTree(AbQueryFilter abQueryFilter) {

        List<GroupVO> groupList = groupMapper.queryGroup(abQueryFilter);
        List<GroupTreeVO> groupTreeList = BeanCopierUtils.transformList(groupList, GroupTreeVO.class);
        return setRootGroup(groupTreeList);
    }

    /**
     * 设置 组织树的根目录
     * 解决 数据权限带来的 组织树断层
     *
     * @param groupTreeList
     * @return
     */
    private List<GroupTreeVO> setRootGroup(List<GroupTreeVO> groupTreeList) {
        final IGroup currentGroup = UserContextUtils.getGroup().orElse(null);
        GroupTreeVO rootGroup = new GroupTreeVO();
        rootGroup.setName("组织");
        rootGroup.setId(StrPool.NUMBER_ZERO);

        if (CollUtil.isEmpty(groupTreeList)) {
            if (UserContextUtils.isSuperAdmin()) {
                return ListUtil.toList(rootGroup);
            }
            if (currentGroup != null) {
                rootGroup.setName(currentGroup.getGroupName());
                rootGroup.setId(currentGroup.getGroupId());
            }
            return ListUtil.toList(rootGroup);
        }
        if (UserContextUtils.isSuperAdmin()) {
            groupTreeList.add(rootGroup);
        }
        return BeanConversionUtils.listToTree(groupTreeList);
    }

    /**
     * 保存组织接口
     *
     * @param saveGroupDTO 组织Dto
     * @return 组织id
     */
    @Override
    public String saveGroup(SaveGroupDTO saveGroupDTO) {
        Group group = BeanCopierUtils.transformBean(saveGroupDTO, Group.class);
        Assert.notNull(group, () -> new BusinessMessage(OrgStatusCode.OPERATION_FAILURE));
        QueryWrapper<Group> queryWrapper = new QueryWrapper();
        //新增组织
        if (StrUtil.isEmpty(group.getId())) {
            Assert.isFalse(
                    exists(queryWrapper.eq("code_", group.getCode())),
                    () -> new BusinessMessage(OrgStatusCode.GROUP_CODE_IS_EXIST));
            String id = IdGeneratorUtils.nextId();
            group.setId(id);
            saveGroupDTO.setId(id);
            if (StrUtil.isNotEmpty(group.getParentId()) && !StrPool.NUMBER_ZERO.equals(group.getParentId())) {
                Group parent = groupMapper.selectById(group.getParentId());
                if (parent != null && StrUtil.isNotEmpty(parent.getPath())) {
                    group.setPath(parent.getPath().concat(StrPool.DOT).concat(group.getId()));
                }
            } else {
                group.setPath(group.getId());
            }
            groupMapper.insert(setEntityPathName(group));
        } else {
            groupMapper.updateById(updatePathName(group));
            //删除旧地组织岗位
            orgRelationManager.removeGroupPostByGroupId(group.getId());
        }
        // 创建新地组织岗位
        if (CollectionUtil.isNotEmpty(saveGroupDTO.getOrgRelationList())) {
            creatOrgRelation(saveGroupDTO);
        }
        return group.getId();
    }

    /**
     * 新增组织时设置pathName
     *
     * @param group 组织
     * @return Group 组织
     */
    private Group setEntityPathName(Group group) {
        if (group.getPath().equals(group.getId())) {
            group.setPathName(group.getName());
        } else {
            List<Group> companyGroup = selectByIds(StrUtil.split(group.getPath(), StrPool.DOT));
            List<String> pathNames = companyGroup.stream().map(Group::getName).collect(Collectors.toList());
            String pathName = CollectionUtil.join(pathNames, StrPool.DASHED);
            group.setPathName(pathName);
        }
        return group;
    }

    /**
     * 更新组织时维护pathName
     *
     * @param group 组织
     * @return group 组织
     */
    private Group updatePathName(Group group) {
        Group oldGroup = groupMapper.selectById(group.getId());
        String oldGroupName = oldGroup.getName();
        //没有修改name
        if (group.getName().equals(oldGroupName) && StrUtil.isNotEmpty(group.getPathName())) {
            return group;
        }

        // 维护所属下级的path
        List<Group> groups = groupMapper.getChildByPath(compatiblePathName(group, group.getName()).getPath() + StrPool.MOD, null);
        //更新组织的path
        for (Group g : groups) groupMapper.updateById(compatiblePathName(g, group.getName()));

        return group;
    }

    /**
     * 兼容没有path的旧数据
     *
     * @param group     组织
     * @param groupName 组织名称
     * @return Group 组织
     */
    private Group compatiblePathName(Group group, String groupName) {
        if (StrUtil.isEmpty(group.getPathName())) {
            return setEntityPathName(group);
        } else {
            group.setPathName(group.getPathName().replace(group.getName(), groupName));
        }
        return group;
    }

    /**
     * 创建组织岗位
     *
     * @param groupDTO 组织DTO
     */
    private void creatOrgRelation(SaveGroupDTO groupDTO) {
        List<OrgRelation> list = BeanCopierUtils.transformList(groupDTO.getOrgRelationList(), OrgRelation.class);

        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(orgRelation -> {
                orgRelation.setGroupId(groupDTO.getId());
                orgRelation.setId(
                        String.format(StrPool.FORMATSTR, groupDTO.getId(), orgRelation.getRoleId()));
            });
            orgRelationManager.bulkCreate(list);
        }
    }

    /**
     * 自定义对话框组织
     *
     * @return List<GroupVO>
     */
    public List<GroupVO> queryGroup(QueryParamDTO paramDTO) {
        List<GroupVO> groups = groupMapper.queryGroup(new DefaultAbQueryFilter(paramDTO));
        List<GroupUserCountVO> groupUserCountVOS = groupMapper.getGroupUserCount();
        for (GroupVO group : groups) {
            String groupId = group.getId();
            for (GroupUserCountVO groupCount : groupUserCountVOS) {
                String id = groupCount.getId();
                if (groupId != null && groupId.equals(id)) {
                    if (!NumberPool.INTEGER_ZERO.equals(groupCount.getUserCount())) {
                        group.setName(group.getName() + StrPool.LPAREN + groupCount.getUserCount() + StrPool.RPAREN);
                        break;
                    }
                }
            }
        }
        return groups;
    }

    /**
     * 组业务接口适配:GroupRelationApiImpl
     * 通过  path 过滤孩子
     *
     * @param path 路径
     * @param type 类型
     * @return 组织集合
     */
    @Override
    public List<Group> getChildByPathAndType(String path, Integer type) {
        if (StrUtil.isEmpty(path)) return Collections.emptyList();
        if (type == null) return groupMapper.getChildByPath(path.concat(StrPool.MOD), null);
        return groupMapper.getChildByPath(path.concat(StrPool.MOD), GroupGradeConstant.valueOfKey(type).getKey());
    }

    /**
     * 组业务接口适配:GroupRelationApiImpl
     * 获取下一级孩子
     *
     * @param parentId 父组织id
     * @return 组织集合
     */
    @Override
    public List<Group> getChildrenByParentId(String parentId) {
        if (StrUtil.isEmpty(parentId)) return Collections.emptyList();
        return groupMapper.getChildrenByParentId(parentId);
    }

    /**
     * 根据用户ID获取组织列表
     * 组业务接口适配:AbGroupApiImpl
     *
     * @param userId 用户id
     * @return 组织结合
     */
    @Override
    public List<Group> getByUserId(String userId) {
        Page<Group> groupPage = groupMapper.getByUserId(PageDTO.of(0, 200, false), userId);
        return groupPage.getRecords();
    }

    /**
     * 通过编码获取组织列表
     * 组业务接口适配:AbGroupApiImpl
     *
     * @param codes 组织编码
     * @return 组织结合
     */
    @Override
    public List<Group> selectByCodes(Collection<String> codes) {
        return groupMapper.selectList(Wrappers.lambdaQuery(Group.class).in(Group::getCode, codes));
    }
}
