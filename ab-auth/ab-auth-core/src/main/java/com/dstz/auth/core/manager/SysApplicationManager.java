package com.dstz.auth.core.manager;

import com.dstz.auth.core.entity.SysApplication;
import com.dstz.auth.rest.model.vo.SysResourceTreeVO;
import com.dstz.base.manager.AbBaseManager;

import java.util.List;

/**
 * <p>
 * 应用 通用业务类
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
public interface SysApplicationManager extends AbBaseManager<SysApplication> {
    /**
     * 判断别名是否存在。
     *
     * @param subsystem
     * @return
     */
    boolean isExist(SysApplication subsystem);

    /**
     * 获取默认子系统。
     * 1.获取用户有权限的系统，如果没有权限则返回空。
     * 2.如果权限子系统，判断是否有默认的子系统，有则返回。
     * 3.否则取第一个。
     *
     * @return
     */
    SysApplication getDefaultSystem(String userId);

    /**
     * 设置默认子系统。
     * 1.如果是默认的则取消。
     * 2.非默认则设置默认。
     *
     * @param systemId
     */
    void setDefaultSystem(String systemId);

    /**
     * 通过应用系统对象获取资源树
     *
     * @param sysApplication 应用对象
     * @return 资源树对象
     */
     List<SysResourceTreeVO> getTreeDataByApplication(SysApplication sysApplication);

    /**
     * 获取当前用户可用系统
     * @return
     */
    List<SysApplication> getCurrentUserSystem();
    /**
     * 根据userId获取系统
     * @return
     */
    List<SysApplication> getSystemByUser(String userId);
    /**
     * 根据code获取系统
     * @return
     */
    SysApplication getByAlias(String code);


    /**
     * 根据id更新应用
     */

    int updateById(SysApplication subsystem);
}
