package com.dstz.sys.core.manager;

import com.dstz.base.manager.AbBaseManager;
import com.dstz.sys.core.entity.SysProperties;

/**
 * <p>
 * 系统属性 通用业务类
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-11
 */
public interface SysPropertiesManager extends AbBaseManager<SysProperties> {

    /**
     * 判断别名是否存在。
     *
     * @param sysProperties
     * @return
     */
    boolean isExist(SysProperties sysProperties);

    /**
     * 重新读取属性配置。
     */
    void reloadProperty();
}
