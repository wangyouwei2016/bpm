package com.dstz.sys.core.manager.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.cache.ICache;
import com.dstz.base.common.constats.AbCacheRegionConstant;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.common.encrypt.EncryptUtil;
import com.dstz.base.common.enums.EnvironmentConstants;
import com.dstz.base.common.property.SysPropertyService;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.sys.core.entity.SysProperties;
import com.dstz.sys.core.manager.SysPropertiesManager;
import com.dstz.sys.core.mapper.SysPropertiesMapper;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 系统属性 通用服务实现类
 *
 * @author jinxia.hou
 * @since 2022-02-11
 */
@Service("sysPropertiesManager")
public class SysPropertiesManagerImpl extends AbBaseManagerImpl<SysProperties> implements SysPropertiesManager, SysPropertyService {

    private final SysPropertiesMapper sysPropertiesMapper;

    private final ICache cache;

    public SysPropertiesManagerImpl(SysPropertiesMapper sysPropertiesMapper, ICache cache) {
        this.sysPropertiesMapper = sysPropertiesMapper;
        this.cache = cache;
    }

    @Override
    public boolean isExist(SysProperties sysProperties) {
        return sysPropertiesMapper.isExist(sysProperties) > 0;
    }

    @Override
    public String getValByCode(String code) {
        final String currentEnv = StrUtil.emptyToDefault(SpringUtil.getActiveProfile(), EnvironmentConstants.DEV.getKey());
        final String cacheKey = StrUtil.join(StrUtil.COLON, currentEnv, code);
        String value = cache.get(AbCacheRegionConstant.PROPERTIES_CACHE_REGION, cacheKey, () -> loadPropertyValue(currentEnv, code));
        return StrUtil.emptyToNull(value);
    }

    private String loadPropertyValue(String environment, String code) {
        LambdaQueryWrapper<SysProperties> queryWrapper = Wrappers.lambdaQuery(SysProperties.class)
                .select(SysProperties::getEncrypt, SysProperties::getValue)
                .in(SysProperties::getEnvironment, StringUtils.upperCase(environment),EnvironmentConstants.DEV.getKey())
                .eq(SysProperties::getCode, code);

        List<SysProperties> sysPropertiesList = sysPropertiesMapper.selectList(queryWrapper);
        
        // 多环境下，取出 指定环境以及dev环境的参数，如果只有一个配置项，返回，如果多个，则过滤出对应环境的返回
        SysProperties sysProperties = sysPropertiesList.size() == 1 ? sysPropertiesList.get(0): null;
        if(sysProperties == null && sysPropertiesList.size()>1) {
        	sysProperties = sysPropertiesList.stream().filter(o-> o.getEnvironment().equals(StringUtils.upperCase(environment))).findFirst().orElse(null);
        }
        
        if (sysProperties == null) {
            return StrUtil.EMPTY;
        }
        return NumberPool.BOOLEAN_TRUE.equals(sysProperties.getEncrypt()) ? EncryptUtil.decrypt(sysProperties.getValue()) : sysProperties.getValue();
    }


    /**
     * 将所有系统属性通过不同环境分组加入进缓存中
     */
    @Override
    public void reloadProperty() {
        cache.invalidateRegion(AbCacheRegionConstant.PROPERTIES_CACHE_REGION);
    }

    @Override
    public Integer getIntByCode(String code) {
        return Convert.toInt(getByCode(code), 0);
    }


    @Override
    public Long getLongByCode(String code) {
        return Convert.toLong(getByCode(code), 0L);
    }

    @Override
    public Boolean getBooleanByCode(String code) {
        return Convert.toBool(getByCode(code), false);
    }
}
