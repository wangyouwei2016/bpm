package com.dstz.org.core.mapper;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.mapper.AbBaseMapper;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.org.core.entity.OrgRelation;
import com.dstz.org.vo.OrgPostVO;
import com.dstz.org.vo.OrgRelationUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xz
 * @since 2022-02-09
 */
@Mapper
public interface OrgRelationMapper extends AbBaseMapper<OrgRelation> {


    /**
     * 查询 组用户
     *
     * @param queueFile 查询过滤器
     * @return 分页组用户
     */
    PageListDTO<OrgRelationUserVO> queryGroupUser(AbQueryFilter queueFile);


    /**
     * 获取用户的 关系
     *
     * @param userId       必须
     * @param relationType 非必须
     * @return 用户关系集合
     */
    List<OrgRelationUserVO> getUserRelation(@Param("userId") String userId, @Param("type") String relationType);

    /**
     * 获取组织岗位
     *
     * @param groupId 组织id
     * @return 岗位集合
     */
    List<OrgRelationUserVO> getGroupPost(String groupId);


    /**
     * 通过 参数查询关系列表
     *
     * @param relationTypes 关系类型
     * @param userId        用户ID
     * @param groupId       组织ID
     * @param roleId        角色ID
     * @return 用户关系列表
     */
    List<OrgRelationUserVO> getRelationsByParam(@Param("relationTypes") List<String> relationTypes, @Param("userId") String userId, @Param("groupId") String groupId, @Param("roleId") String roleId);


    /**
     * @param relation userId,roleId,groupId,relation 存在则相等判断
     * @param relation 存在则非匹配
     * @return 查询角色是否已存在
     */
    int getCountByRelation(OrgRelation relation);

    /**
     * 通过 userId 刪除所有关系
     *
     * @param userId 用户id
     */
    void removeByUserId(Serializable userId);


    /**
     * 删除 组下的岗位
     *
     * @param groupId 组织id
     */
    void removeGroupPostByGroupId(String groupId);

    /**
     * 查询用户集合
     *
     * @param queryFilter 查询过滤
     * @return 用户集合
     */
    PageListDTO<OrgRelationUserVO> queryRoleUser(AbQueryFilter queryFilter);

    /**
     * 根据角色ID集和组织ID集获取岗位
     *
     * @param roleIds  角色ID集
     * @param groupIds 组织ID集
     * @return 岗位
     */
    List<OrgPostVO> getPostsByRoleIdsAndGroupIds(@Param("roleIds") Collection<String> roleIds, @Param("groupIds") Collection<String> groupIds);


    Collection<String> getUserRelationIds(@Param("ids") Collection<? extends Serializable> ids);

    /**
     * 查询岗位集合
     *
     * @param queryFilter 查询参数过滤
     * @return 分页列表
     */
    PageListDTO<OrgPostVO> queryPosts(AbQueryFilter queryFilter);
}
