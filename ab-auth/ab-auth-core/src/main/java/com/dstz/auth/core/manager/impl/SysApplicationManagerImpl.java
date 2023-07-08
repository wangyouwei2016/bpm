package com.dstz.auth.core.manager.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.auth.core.entity.SysApplication;
import com.dstz.auth.core.entity.SysResource;
import com.dstz.auth.core.manager.SysApplicationManager;
import com.dstz.auth.core.manager.SysResourceManager;
import com.dstz.auth.core.mapper.ResourceRoleMapper;
import com.dstz.auth.core.mapper.SysApplicationMapper;
import com.dstz.auth.rest.model.vo.SysResourceTreeVO;
import com.dstz.base.common.constats.AbCacheRegionConstant;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.common.context.UserContext;
import com.dstz.base.common.utils.BeanConversionUtils;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.org.api.GroupApi;
import com.dstz.org.api.enums.GroupType;
import com.dstz.org.api.model.IGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 应用 通用服务实现类
 *
 * @author lightning
 * @since 2022-02-07
 */
@Service("sysApplicationManager")
public class SysApplicationManagerImpl extends AbBaseManagerImpl<SysApplication> implements SysApplicationManager {
    @Autowired
    SysApplicationMapper sysApplicationMapper;
    @Autowired
    SysResourceManager sysResourceManager;
    @Autowired
    private ResourceRoleMapper resourceRoleMapper;
    @Autowired
    private GroupApi groupApi;

    @Override
    public boolean isExist(SysApplication subsystem) {
        return sysApplicationMapper.isExist(subsystem) > 0;
    }

    /**
     * 通过应用系统对象获取资源树
     *
     * @param sysApplication 应用对象
     * @return 资源树对象
     */
    @Override
    public List<SysResourceTreeVO> getTreeDataByApplication(SysApplication sysApplication) {
        List<SysResource> resourceList = sysResourceManager.getBySystemId(sysApplication.getId());
        List<SysResourceTreeVO> groupList = BeanCopierUtils.transformList(resourceList, SysResourceTreeVO.class);
        if (CollectionUtil.isEmpty(groupList)) {
            groupList = new ArrayList<>();
        }
        SysResourceTreeVO rootResource = new SysResourceTreeVO();
        rootResource.setName(sysApplication.getName());
        rootResource.setId(NumberPool.INTEGER_ZERO.toString());
        // 根节点
        rootResource.setAppId(sysApplication.getId());
        groupList.add(rootResource);
        return BeanConversionUtils.listToTree(groupList);
    }

    @Override
    public SysApplication getDefaultSystem(String userId) {
        List<SysApplication> sysApplications = getSystemByUser(userId);
        if (CollectionUtil.isEmpty(sysApplications)) {
            return null;
        }

        return Optional.ofNullable(CollUtil.findOne(sysApplications, app -> NumberPool.BOOLEAN_TRUE.equals(app.getIsDefault())))
                .orElseGet(() -> CollUtil.getFirst(sysApplications));
    }

    @Override
    public void setDefaultSystem(String systemId) {
        SysApplication subSystem = sysApplicationMapper.selectById(systemId);
        if (subSystem.getIsDefault() == NumberPool.BOOLEAN_TRUE) {
            subSystem.setIsDefault(NumberPool.BOOLEAN_FALSE);
        } else {
            sysApplicationMapper.updNoDefault();
            subSystem.setIsDefault(NumberPool.BOOLEAN_TRUE);
        }
        sysApplicationMapper.updateById(subSystem);
    }


    @Override
    public List<SysApplication> getCurrentUserSystem() {
        UserContext userContext = UserContextUtils.getUserContext();
        if (userContext.isSuperAdmin()) {
            LambdaQueryWrapper<SysApplication> queryWrapper = Wrappers.lambdaQuery(SysApplication.class).eq(SysApplication::getEnabled, NumberPool.BOOLEAN_TRUE);
            return sysApplicationMapper.selectList(queryWrapper);
        }

        // 获取用户角色编码
        Collection<String> authorities = userContext.getAuthorities();
        if (CollUtil.isEmpty(authorities)) {
            return new ArrayList<>();
        }

        // 获取当前用户角色列表
        List<String> roleIds = Optional.ofNullable(groupApi.getByGroupCodes(GroupType.ROLE.getType(), authorities))
                .map(IterUtil::asIterable)
                .map(iter -> CollUtil.map(iter, IGroup::getGroupId, true))
                .orElse(null);

        // 角色ID未获取到返回空列表
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }

        // 角色ID获取关联的应用ID
        Set<String> appIds = resourceRoleMapper.selectAppIdByRoleIds(roleIds);
        if (CollUtil.isEmpty(appIds)) {
            return new ArrayList<>();
        }

        // 查询应用列表
        LambdaQueryWrapper<SysApplication> queryWrapper = Wrappers.lambdaQuery(SysApplication.class).in(SysApplication::getId, appIds).eq(SysApplication::getEnabled, NumberPool.BOOLEAN_TRUE);
        return sysApplicationMapper.selectList(queryWrapper);
    }

    @Cacheable(cacheNames = AbCacheRegionConstant.SYS_APPLICATION, key = "#code")
    @Override
    public SysApplication getByAlias(String code) {
        return sysApplicationMapper.getByAlias(code);
    }

    @CacheEvict(cacheNames = AbCacheRegionConstant.SYS_APPLICATION, key = "#subsystem.code", allEntries = true)
    @Override
    public int updateById(SysApplication subsystem) {
        return sysApplicationMapper.updateById(subsystem);
    }

    @Override
    public List<SysApplication> getSystemByUser(String userId) {
        List<String> roleIds = CollUtil.map(groupApi.getByGroupTypeAndUserId(GroupType.ROLE.getType(), userId), IGroup::getGroupId, true);
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        Set<String> appIds = resourceRoleMapper.selectAppIdByRoleIds(roleIds);
        if (CollUtil.isNotEmpty(appIds)) {
            return new ArrayList<>();
        }

        // 查询应用列表
        LambdaQueryWrapper<SysApplication> queryWrapper = Wrappers.lambdaQuery(SysApplication.class).in(SysApplication::getId, appIds).eq(SysApplication::getEnabled, NumberPool.BOOLEAN_TRUE);
        return sysApplicationMapper.selectList(queryWrapper);
    }
}
