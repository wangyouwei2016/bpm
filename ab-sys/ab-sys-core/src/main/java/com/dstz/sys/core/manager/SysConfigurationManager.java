package com.dstz.sys.core.manager;

import com.dstz.sys.core.entity.SysConfiguration;
import com.dstz.base.manager.AbBaseManager;

/**
 * <p>
 *  通用业务类
 * </p>
 *
 * @author lightning
 * @since 2023-05-11
 */
public interface SysConfigurationManager extends AbBaseManager<SysConfiguration> {

    /**
     * 根据编码获取系统配置
     * @param code 配置编码
     * @return 配置信息
     */
    String getConfByCode(String code);

}
