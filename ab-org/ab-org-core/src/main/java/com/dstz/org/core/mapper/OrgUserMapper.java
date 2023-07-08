package com.dstz.org.core.mapper;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.mapper.AbBaseMapper;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.org.core.entity.OrgUser;
import com.dstz.org.vo.OrgUserListJsonVO;
import com.dstz.org.vo.ResourceUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 Mapper 接口
 *
 * @author xz
 * @since 2022-02-07
 */
@Mapper
public interface OrgUserMapper extends AbBaseMapper<OrgUser> {


    /**
     * 判断用户是否存在。
     */
    Integer isUserExist(OrgUser user);

    /**
     * 根据角色ID获取用户列表
     *
     * @param relId 角色id
     * @param type  关系类型
     * @return 用户集合
     */
    List<OrgUser> getUserListByRelation(@Param("relationId") String relId, @Param("relationType") String type);

    /**
     * 获取岗位下的所有用户
     *
     * @param roleId  角色id
     * @param groupId 组织id
     * @return 用户集合
     */
    List<OrgUser> getUserListByPost(@Param("roleId") String roleId, @Param("groupId") String groupId);

    /**
     * 获取指定组织路径下的所有用户
     *
     * @param path 路径
     * @return 用户集合
     */
    List<OrgUser> getUserListByGroupPath(@Param("path") String path);

    /**
     * 根据主键选择性更新记录
     *
     * @param record 编码
     * @return 删除数量
     */
    int updateByPrimaryKeySelective(OrgUser record);

    /**
     * 用户权限查询接口
     *
     * @param queryFilter 查询过滤器
     * @return 权限用户集合
     */
    PageListDTO<ResourceUserVO> getUserByResource(AbQueryFilter queryFilter);

    /**
     * 用户权限查询接口
     *
     * @param queryFilter 查询过滤器
     * @return 分页用户集合
     */
    PageListDTO<OrgUser> queryUser(AbQueryFilter queryFilter);

    /**
     * queryFilter 分页列表查询
     *
     * @param abQueryFilter
     * @return
     */
    PageListDTO<OrgUserListJsonVO> queryUserList(AbQueryFilter abQueryFilter);

}
