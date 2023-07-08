package com.dstz.org.rest.controller;


import cn.hutool.core.collection.CollUtil;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.utils.BeanConversionUtils;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.org.core.entity.Group;
import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.dto.SaveGroupDTO;
import com.dstz.org.vo.GroupTreeVO;
import com.dstz.org.vo.GroupVO;
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
 * 组织架构 前端控制器
 * </p>
 *
 * @author xz
 * @since 2022-02-07
 */
@RestController
@RequestMapping(AbAppRestConstant.ORG_SERVICE_PREFIX + "/group")
public class GroupController extends AbCrudController<Group> {

    /**
     * 允许 fullname，account,roleId 作为入参传入列表页
     */
    final Set<String> accessQueryFilters = CollUtil.newHashSet("fullname", "account", "roleId", "id", "groupId");
    @Autowired
    GroupManager groupManager;

    @Override
    public Set<String> getAccessQueryFilters() {
        return accessQueryFilters;
    }

    /**
     * 组织架构明细页面
     *
     * @param id 组织id
     * @return 组织
     */
    @RequestMapping(value = "getGroupVo")
    public ApiResponse<GroupVO> getGroupVo(@NotBlank(message = "参数不能为空") @RequestParam String id) {
        return success(groupManager.getGroupVo(id));
    }

    /**
     * 获取组织树
     *
     * @return 树形组织集合
     */
    @RequestMapping(value = "getOrgTree")
    public ApiResponse<List<GroupTreeVO>> getOrgTree(@RequestBody QueryParamDTO paramDTO) {
        DefaultAbQueryFilter abQueryFilter = new DefaultAbQueryFilter(paramDTO);
        abQueryFilter.noPage();
        return success(groupManager.getOrgTree(abQueryFilter));
    }

    /**
     * 保存组织
     *
     * @param saveGroupDTO 保存组织DTO
     * @return ApiResponse  响应结果
     */
    @RequestMapping(value = "saveGroup")
    public ApiResponse<String> saveGroup(@Valid @RequestBody SaveGroupDTO saveGroupDTO) {
        return success(groupManager.saveGroup(saveGroupDTO));
    }

    @Override
    protected String getEntityDesc() {
        return "组织";
    }

}
