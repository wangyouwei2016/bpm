package com.dstz.org.core.manager;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.manager.AbBaseManager;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.org.core.entity.OrgRelation;
import com.dstz.org.dto.BatchSaveRelationDTO;
import com.dstz.org.dto.RemoveCheckRelationDTO;
import com.dstz.org.dto.SaveGroupUserRelDTO;
import com.dstz.org.dto.SaveRoleUsersDTO;
import com.dstz.org.vo.OrgRelationUserVO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 通用业务类
 * </p>
 *
 * @author xz
 * @since 2022-02-09
 */
public interface OrgRelationManager extends AbBaseManager<OrgRelation> {


    /**
     * 查询组用户
     *
     * @param queryFilter 查询过滤器
     * @return 分页结果
     */
    PageListDTO<OrgRelationUserVO> queryGroupUser(AbQueryFilter queryFilter);

    /**
     * 更新组织关系是否为主岗位
     *
     * @param id 岗位id
     */
    void setMaster(String id);

    /**
     * 修改状态
     *
     * @param id 岗位id
     */
    String changeStatus(String id);

    /**
     * 获取组织上的岗位
     *
     * @param groupId 组织id
     * @return 岗位集合
     */
    List<OrgRelationUserVO> getGroupPost(String groupId);

    /**
     * 保存 用户与组织的关系
     *
     * @param saveGroupUserRelDTO 保存岗位DTO
     */
    void saveGroupUserRel(SaveGroupUserRelDTO saveGroupUserRelDTO);


    /**
     * 保存 用户与角色的关系
     *
     * @param saveRoleUsersDTO 保存角色DTO
     * @return 保存数量
     */
    int saveRoleUsers(SaveRoleUsersDTO saveRoleUsersDTO);

    /**
     * 查询用户集合
     *
     * @param queryFilter 查询过滤器
     * @return 分页用户集合
     */
    PageListDTO<OrgRelationUserVO> queryRoleUser(AbQueryFilter queryFilter);

    /**
     * 删除前校验
     *
     * @param removeCheckRelationDTO 删除校验DTO
     */
    void beforeRemoveRelCheck(RemoveCheckRelationDTO removeCheckRelationDTO);

    /**
     * 批量保存组织角色关系
     *
     * @param batchSaveRelationDTO 批量保存DTO
     */
    void batchSave(BatchSaveRelationDTO batchSaveRelationDTO);

    /**
     * 获取用户的所有关系
     *
     * @param userId       用户id
     * @param relationType 关系类型
     * @return 关联用户集合
     */
    List<OrgRelationUserVO> getUserRelation(String userId, String relationType);

    /**
     * 删除 组织上的岗位
     *
     * @param groupId 组织id
     */
    void removeGroupPostByGroupId(String groupId);

    void  removeByUserId(String userId);

    Collection<? extends Serializable>  getUserRelationIds(Collection<? extends Serializable> ids);

}
