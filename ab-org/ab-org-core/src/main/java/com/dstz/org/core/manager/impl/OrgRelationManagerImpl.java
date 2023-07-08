package com.dstz.org.core.manager.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.events.AbUserEvent;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.ConditionType;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.org.enums.RelationTypeConstant;
import com.dstz.org.core.constant.OrgStatusCode;
import com.dstz.org.core.entity.OrgRelation;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.mapper.OrgRelationMapper;
import com.dstz.org.dto.BatchSaveRelationDTO;
import com.dstz.org.dto.RemoveCheckRelationDTO;
import com.dstz.org.dto.SaveGroupUserRelDTO;
import com.dstz.org.dto.SaveRoleUsersDTO;
import com.dstz.org.vo.OrgRelationUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 通用服务实现类
 *
 * @author xz
 * @since 2022-02-09
 */
@Service("orgRelationManager")
public class OrgRelationManagerImpl extends AbBaseManagerImpl<OrgRelation> implements OrgRelationManager {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrgRelationMapper orgRelationMapper;


    /**
     * 查询组用户
     *
     * @param filter  查询参数
     * @return OrgRelationUserVO集合
     */
    @Override
    public PageListDTO<OrgRelationUserVO> queryGroupUser(AbQueryFilter filter) {
        return orgRelationMapper.queryGroupUser(filter);
    }

    /**
     * 设置主组织 修改所有子组织
     *
     * @param id 关系变id
     */
    @Override
    public void setMaster(String id) {
        OrgRelation orgRelation = orgRelationMapper.selectById(id);
        if (orgRelation == null || StrUtil.isEmpty(orgRelation.getUserId())) return;

        //关系类型
        List<String> relationList = Arrays.asList(RelationTypeConstant.GROUP_USER.getKey(), RelationTypeConstant.POST_USER.getKey());
        //查询出用户 与 岗位，组织的所有关系，置为 非主版本
        List<OrgRelationUserVO> userGroupRelations = orgRelationMapper.getRelationsByParam(relationList, orgRelation.getUserId(), null, null);

        //设置
        if (CollectionUtil.isNotEmpty(userGroupRelations)) {
            userGroupRelations.forEach(rel -> {
                if (NumberPool.INTEGER_ONE.equals(rel.getIsMaster())) {
                    rel.setIsMaster(NumberPool.INTEGER_ZERO);
                    update(BeanCopierUtils.transformBean(rel, OrgRelation.class));
                }
            });
        }
        //切换是否主版本
        orgRelation.setIsMaster(NumberPool.INTEGER_ZERO.equals(orgRelation.getIsMaster()) ? NumberPool.INTEGER_ONE : NumberPool.INTEGER_ZERO);
        update(orgRelation);
    }

    /**
     * 修改岗位状态
     *
     * @param id  关系表id
     */
    @Override
    public String changeStatus(String id) {
        OrgRelation orgRelation = orgRelationMapper.selectById(id);
        if (orgRelation == null) return StrPool.FALSE;

        //修改状态更新数据
        orgRelation.setStatus(NumberPool.INTEGER_ZERO.equals(orgRelation.getStatus()) ? NumberPool.INTEGER_ONE : NumberPool.INTEGER_ZERO);
        update(orgRelation);

        String userId = orgRelation.getUserId();
        if (StrUtil.isEmpty(userId)) return StrPool.FALSE;
        // 当前用户修改了自己的信息，需要更新缓存数据
        if (StrUtil.equals(UserContextUtils.getUserId(), userId)) return StrPool.TRUE;
        return StrPool.FALSE;
    }

    /**
     * 获取组织岗位
     *
     * @param groupId 组织id
     * @return OrgRelationUserVO集合
     */
    @Override
    public List<OrgRelationUserVO> getGroupPost(String groupId) {
        return orgRelationMapper.getGroupPost(groupId);
    }

    /**
     * 保存组织岗位
     *
     * @param saveGroupUserRelDTO  组织岗位DTO
     */
    @Override
    public void saveGroupUserRel(SaveGroupUserRelDTO saveGroupUserRelDTO) {
        for (String userId : saveGroupUserRelDTO.getUserIds()) {
            if (StrUtil.isEmpty(userId)) continue;
            //构建OrgRelation
            OrgRelation orgRelation = new OrgRelation(saveGroupUserRelDTO.getGroupId(), userId, null, RelationTypeConstant.GROUP_USER.getKey());

            if (ArrayUtil.isNotEmpty(saveGroupUserRelDTO.getRoleIds())) {
                for (String roleId : saveGroupUserRelDTO.getRoleIds()) {
                    //设置参数
                    orgRelation.setRoleId(roleId);
                    orgRelation.setType(RelationTypeConstant.POST_USER.getKey());
                    // 不存在则创建
                    insertRel(orgRelation);
                }
                continue;
            }
            //不存在时 添加数据
            insertRel(orgRelation);
        }
    }

    /**
     * 保存角色用户
     *
     * @param saveRoleUsersDTO  角色用户DTO
     * @return  添加数量
     */
    @Override
    public int saveRoleUsers(SaveRoleUsersDTO saveRoleUsersDTO) {
        final String roleId = saveRoleUsersDTO.getRoleId();
        int i = 0;
        for (String userId : saveRoleUsersDTO.getUserIds()) {
            OrgRelation orgRelation = new OrgRelation(null, userId, roleId, RelationTypeConstant.USER_ROLE.getKey());
            if (checkRelationIsExist(orgRelation)) {
                log.warn("关系重复添加，已跳过  {}", JsonUtils.toJSONString(orgRelation));
                continue;
            }
            i++;
            orgRelationMapper.insert(orgRelation);
        }
        return i;
    }

    /**
     * * 删除用户 需要删除关联关系
     *
     * @param ids 实体ID集
     * @return 删除用户数量
     */
    @Override
    public int removeByIds(Collection<? extends Serializable> ids) {
        Collection<String> relIds=getUserRelationIds(ids);
        if (CollectionUtil.isEmpty(relIds)){
            return super.removeByIds(ids);
        };
        return super.removeByIds(relIds);
    }

    @Override
    public Collection<String> getUserRelationIds(Collection<? extends Serializable> ids){
        return  orgRelationMapper.getUserRelationIds(ids);
    }


    /**
     * 查询组用户
     *
     * @param queryFilter 查询过滤
     * @return 分页查询结果
     */
    @Override
    public PageListDTO<OrgRelationUserVO> queryRoleUser(AbQueryFilter queryFilter) {
        return orgRelationMapper.queryRoleUser(queryFilter);
    }

    /**
     * 删除 角色、删除组织、删除岗位前进行校验
     * 删除角色 校验 岗位、岗位人员、角色人员是否存在
     * 删除组织、 校验岗位、组织人员
     * 删除岗位  校验岗位人员
     *
     * @param removeCheckRelationDTO  删除校验DTO
     */
    @Override
    public void beforeRemoveRelCheck(RemoveCheckRelationDTO removeCheckRelationDTO) {
        String groupId = removeCheckRelationDTO.getGroupId();
        String roleId = removeCheckRelationDTO.getRoleId();
        // 通过  关系查询 用户
        AbQueryFilter filter = DefaultAbQueryFilter.build();
        filter.getPage().setSearchCount(true);
        // 岗位检查 岗位下人员是否存在
        if (StrUtil.isNotEmpty(groupId))
            filter.addFilter("relation.group_id_", groupId, ConditionType.EQUAL);

        if (StrUtil.isNotEmpty(roleId))
            filter.addFilter("relation.role_id_", roleId, ConditionType.EQUAL);

        if (StrUtil.isNotEmpty(roleId) && StrUtil.isNotEmpty(groupId))
            filter.addFilter("relation.type_", RelationTypeConstant.POST.getKey(), ConditionType.NOT_EQUAL);

        PageListDTO<OrgRelationUserVO> pageListDTO = orgRelationMapper.queryRoleUser(filter);
        if (pageListDTO == null || CollUtil.isEmpty(pageListDTO.getRows())) return;

        StringBuilder sb = new StringBuilder("请先移除以下关系：<br>");
        for (OrgRelationUserVO orgRelationUserVO : pageListDTO) {
            getRelationNotes(orgRelationUserVO, sb);
        }
        if (pageListDTO.getTotal() > 10) sb.append("......<br>");

        sb.append(" 共[").append(pageListDTO.getTotal()).append("]条，待移除关系");
        throw new BusinessMessage(OrgStatusCode.ORG_RELATION_REMOVE_CHECK.formatDefaultMessage(sb.toString()));
    }

    private void getRelationNotes(OrgRelationUserVO relation, StringBuilder sb) {
        // 岗位
        if (RelationTypeConstant.POST.getKey().equals(relation.getType())) {
            sb.append("岗位：").append(relation.getPostName());
        } else if (RelationTypeConstant.POST_USER.getKey().equals(relation.getType())) {
            sb.append("岗位 [").append(relation.getPostName()).append("] 下用户：").append(relation.getUserFullname());
        } else if (RelationTypeConstant.GROUP_USER.getKey().equals(relation.getType())) {
            sb.append("组织 [").append(relation.getGroupName()).append("] 下用户：").append(relation.getUserFullname());
        } else if (RelationTypeConstant.USER_ROLE.getKey().equals(relation.getType())) {
            sb.append("角色 [").append(relation.getRoleName()).append("] 下用户：").append(relation.getUserFullname());
        }
        sb.append("<br>");
    }

    /**
     * 批量保存组织角色关系
     *
     * @param batchSaveRelationDTO  批量保存DTO
     */
    @Override
    public void batchSave(BatchSaveRelationDTO batchSaveRelationDTO) {
        String[] roleIds = batchSaveRelationDTO.getRoleIds().split(StrPool.COMMA);
        String[] groupIds = batchSaveRelationDTO.getGroupIds().split(StrPool.COMMA);
        Arrays.asList(roleIds).forEach(roleId ->
                Arrays.asList(groupIds).forEach(groupId -> {
                    OrgRelation orgRelation = new OrgRelation(groupId, null, roleId, RelationTypeConstant.POST.getKey());
                    insertRel(orgRelation);
                })
        );
    }


    /**
     * 根据组织id删除关系
     *
     * @param id   组织id
     */
    @Override
    public void removeGroupPostByGroupId(String id) {
        orgRelationMapper.removeGroupPostByGroupId(id);
    }

    /**
     * 获取用户的所有关系
     *
     * @param userId  用户id
     * @param relationType  关系类型
     * @return  OrgRelationUserVO集合
     */
    @Override
    public List<OrgRelationUserVO> getUserRelation(String userId, String relationType) {
        List<OrgRelationUserVO> orgRelationUserVOS= orgRelationMapper.getUserRelation(userId, relationType);
        if (CollectionUtil.isEmpty(orgRelationUserVOS)){
            return  Collections.emptyList();
        }
        return orgRelationUserVOS;
    }

    /**
     * 查询角色是否已存在
     *
     * @param orgRelation  角色关系
     * @return boolean
     */
    private boolean checkRelationIsExist(OrgRelation orgRelation) {
        return orgRelationMapper.getCountByRelation(orgRelation) > NumberPool.INTEGER_ZERO;
    }

    /**
     * 保存关联关系
     *
     * @param orgRelation  角色关系
     */
    private void insertRel(OrgRelation orgRelation) {
        // 不存在则创建
        if (! checkRelationIsExist(orgRelation)) {
            orgRelationMapper.insert(orgRelation);
        } else {
            log.warn("关系重复添加，已跳过  {}", JsonUtils.toJSONString(orgRelation));
        }
    }

    @Override
    public void removeByUserId(String userId) {
        if (StrUtil.isNotEmpty(userId)){
            orgRelationMapper.removeByUserId(userId);
        }
    }
}

