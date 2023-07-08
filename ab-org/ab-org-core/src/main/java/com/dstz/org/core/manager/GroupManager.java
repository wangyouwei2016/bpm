package com.dstz.org.core.manager;

import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.manager.AbBaseManager;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.org.core.entity.Group;
import com.dstz.org.dto.SaveGroupDTO;
import com.dstz.org.vo.GroupTreeVO;
import com.dstz.org.vo.GroupVO;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 组织架构 通用业务类
 * </p>
 *
 * @author xz
 * @since 2022-02-07
 */
public interface GroupManager extends AbBaseManager<Group> {


    /**
     * 组织树接口
     *
     * @param queryParamDTO 查询条件DTO
     * @return 组织集合
     */
    List<GroupVO> queryGroup(QueryParamDTO queryParamDTO);

    /**
     * 获取组织信息
     *
     * @param id 组织id
     * @return 组织
     */
    GroupVO getGroupVo(String id);

    /**
     * 保存组织
     *
     * @param saveGroupDTO 保存组织DTO
     * @return 组织id
     */
    String saveGroup(SaveGroupDTO saveGroupDTO);

    /**
     * 获取组织树
     *
     * @return 树形组织集合
     */
    List<GroupTreeVO> getOrgTree(AbQueryFilter abQueryFilter);

    /**
     * 通过  path 过滤孩子
     * 组业务接口适配:GroupRelationApiImpl
     *
     * @param path 路径
     * @param type 类型
     * @return 组织集合
     */
    List<Group> getChildByPathAndType(String path, Integer type);

    /**
     * 获取下一级孩子
     * 组业务接口适配:GroupRelationApiImpl
     *
     * @param parentId 父组织id
     * @return 组织集合
     */
    List<Group> getChildrenByParentId(String parentId);

    /**
     * 通过编码获取组织列表
     * 组业务接口适配:AbGroupApiImpl
     *
     * @param codes 组织编码
     * @return 组织集合
     */
    List<Group> selectByCodes(Collection<String> codes);

    /**
     * 根据用户ID获取组织列表
     * 组业务接口适配:AbGroupApiImpl
     *
     * @param userId 用户id
     * @return 组织集合
     */
    List<Group> getByUserId(String userId);
}
