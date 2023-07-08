package com.dstz.org.rest.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.api.vo.PageListVO;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.events.AbUserEvent;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.ConditionType;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.org.enums.RelationTypeConstant;
import com.dstz.org.core.entity.OrgRelation;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.dto.BatchSaveRelationDTO;
import com.dstz.org.dto.RemoveCheckRelationDTO;
import com.dstz.org.dto.SaveGroupUserRelDTO;
import com.dstz.org.dto.SaveRoleUsersDTO;
import com.dstz.org.vo.OrgRelationUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

import static com.dstz.base.api.vo.ApiResponse.success;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xz
 * @since 2022-02-09
 */
@RestController
@RequestMapping(AbAppRestConstant.ORG_SERVICE_PREFIX + "/orgRelation")
public class OrgRelationController extends AbCrudController<OrgRelation> {

    /**
     * 允许groupId,roleId 作为入参传入列表页
     */
    final Set<String> accessQueryFilters = CollUtil.newHashSet("groupId", "roleId");
    @Autowired
    OrgRelationManager orgRelationManager;

    @Override
    protected String getEntityDesc() {
        return "用户组织关系";
    }

    /**
     * 查询 组织用户
     *
     * @param queryParamDto 查询参数
     * @return 组织用户集合
     */
    @RequestMapping(value = "queryGroupUser")
    public ApiResponse<PageListVO<OrgRelationUserVO>> queryGroupUser(@Valid @RequestBody QueryParamDTO queryParamDto) {
        //查询 岗位 和 用户组的关系
        AbQueryFilter filter = new DefaultAbQueryFilter(queryParamDto)
                .addFilter("tgroup.id", queryParamDto.getQueryParam().get("groupId"), ConditionType.EQUAL)
                .addFilter("relation.type", RelationTypeConstant.POST.getKey(), ConditionType.NOT_EQUAL);

        return success(orgRelationManager.queryGroupUser(filter));
    }

    /**
     * 设置主组织
     *
     * @param id 岗位id
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "setMaster")
    public ApiResponse<String> setMaster(@NotBlank(message = "参数不能为空") @RequestParam(name = "id") String id) {
        return success(() -> orgRelationManager.setMaster(id));
    }

    /**
     * 修改状态
     *
     * @param id 岗位id
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "updateStatus")
    public ApiResponse<String> updateStatus(@NotBlank(message = "参数不能为空") @RequestParam(name = "id") String id) {
        String str = orgRelationManager.changeStatus(id);
        if (StrUtil.equals(StrPool.TRUE,str)){
            SpringUtil.publishEvent(new AbUserEvent(CollectionUtil.newArrayList(UserContextUtils.getAccount()), AbUserEvent.EventType.UPDATE_USER));
        }
        return success();
    }

    /**
     * 获取组织岗位
     *
     * @param groupId 组织id
     * @return 组织角色集合
     */
    @RequestMapping(value = "getGroupPost")
    public ApiResponse<List<OrgRelationUserVO>> getGroupPost(@NotBlank(message = "参数不能为空") @RequestParam(name = "groupId") String groupId) {
        List<OrgRelationUserVO> orgRelations = orgRelationManager.getGroupPost(groupId);
        return success(orgRelations);
    }

    /**
     * 保存组织岗位
     *
     * @param saveGroupUserRelDTO 保存岗位DTO
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "saveGroupUserRel")
    public ApiResponse<String> saveGroupUserRel(@Valid @RequestBody SaveGroupUserRelDTO saveGroupUserRelDTO) {
        return success(() -> orgRelationManager.saveGroupUserRel(saveGroupUserRelDTO));
    }

    /**
     * 保存角色用户
     *
     * @param saveRoleUsersDTO 保存角色DTO
     * @return ApiResponse  响应对象
     */
    @RequestMapping("saveRoleUsers")
    public ApiResponse<String> saveRoleUsers(@Valid @RequestBody SaveRoleUsersDTO saveRoleUsersDTO) {
        String desc = String.format("%d条用户角色添加成功", orgRelationManager.saveRoleUsers(saveRoleUsersDTO));
        return success(desc);
    }

    /**
     * 查询用户集合
     *
     * @param queryParamDto 查询组用户
     * @return 用户集合
     */
    @RequestMapping(value = "roleJson")
    public ApiResponse<PageListVO<OrgRelationUserVO>> roleJson(@Valid @RequestBody QueryParamDTO queryParamDto) {
        //查询 岗位 和 用户组的关系
        AbQueryFilter filter = new DefaultAbQueryFilter(queryParamDto)
                .addFilter("role.id", queryParamDto.getQueryParam().get("roleId"), ConditionType.EQUAL)
                .addFilter("relation.type", RelationTypeConstant.POST.getKey(), ConditionType.NOT_EQUAL);
        return success(orgRelationManager.queryRoleUser(filter));
    }

    /**
     * 删除 角色、删除组织、删除岗位前进行校验
     * 删除角色 校验 岗位、岗位人员、角色人员是否存在
     * 删除组织、 校验岗位、组织人员
     * 删除岗位  校验岗位人员
     *
     * @param removeCheckRelationDTO 删除校验DTO
     * @return ApiResponse  响应对象
     */
    @RequestMapping(value = "removeRelCheck")
    public ApiResponse<String> removeRelCheck(@RequestBody RemoveCheckRelationDTO removeCheckRelationDTO) {
        return success(() -> orgRelationManager.beforeRemoveRelCheck(removeCheckRelationDTO));
    }

    /**
     * 批量设置组织角色关联关系
     *
     * @param batchSaveRelationDTO 疲劳保存DTO
     * @return ApiResponse   响应对象
     */
    @RequestMapping(value = "batchSave")
    public ApiResponse<String> batchSave(@Valid @RequestBody BatchSaveRelationDTO batchSaveRelationDTO) {
        return success(() -> orgRelationManager.batchSave(batchSaveRelationDTO));
    }

    @Override
    public Set<String> getAccessQueryFilters() {
        return accessQueryFilters;
    }
}
