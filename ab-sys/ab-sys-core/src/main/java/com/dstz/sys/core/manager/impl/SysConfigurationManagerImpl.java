package com.dstz.sys.core.manager.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.sys.core.entity.SysConfiguration;
import com.dstz.sys.core.manager.SysConfigurationManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通用服务实现类
 *
 * @author lightning
 * @since 2023-05-11
 */
@Service("sysConfigurationManager")
public class SysConfigurationManagerImpl extends AbBaseManagerImpl<SysConfiguration> implements SysConfigurationManager {

    @Override
    public String getConfByCode(String code) {
        List<SysConfiguration> sysConfigurationList = this.selectByWrapper(Wrappers.lambdaQuery(SysConfiguration.class).eq(SysConfiguration::getConfType,code).eq(SysConfiguration::getIsEnable, NumberPool.INTEGER_ONE));
        return CollUtil.isEmpty(sysConfigurationList)?"":sysConfigurationList.get(0).getConfJson();
    }
}
