package com.dstz.org.core.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dstz.base.mapper.AbBaseMapper;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.org.core.entity.Group;
import com.dstz.org.vo.GroupUserCountVO;
import com.dstz.org.vo.GroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织架构 Mapper 接口
 * </p>
 *
 * @author xz
 * @since 2022-02-07
 */
@Mapper
public interface GroupMapper extends AbBaseMapper<Group> {

    void removeAll();

    /**
     * @param queryFilter 查询过滤器
     * @return 组织集合
     */
    List<GroupVO> queryGroup(AbQueryFilter queryFilter);

    /**
     * 查询组织用户接口
     *
     * @return 组织用户数量VO
     */
    List<GroupUserCountVO> getGroupUserCount();

    /**
     * 通过  path 过滤孩子
     * 组业务接口适配:GroupRelationApiImpl
     *
     * @param path 路径
     * @param type 类型
     * @return 组织集合
     */
    List<Group> getChildByPath(@Param("path") String path, @Param("type") String type);

    /**
     * 获取下一级孩子
     * 组业务接口适配:GroupRelationApiImpl
     *
     * @param parentId 父组织id
     * @return 组织集合
     */
    List<Group> getChildrenByParentId(String parentId);


    /**
     * 根据用户ID获取组织列表，含岗位，组织，第一个为主组织
     * 组业务接口适配:AbGroupApiImpl
     *
     * @param page   分页
     * @param userId 用户id
     * @return 组织集合
     */
    <P extends IPage<Group>> P getByUserId(P page, @Param("userId") String userId);

}
