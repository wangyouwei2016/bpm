package com.dstz.springboot.autoconfigure.cache;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.cache.CacheRegion;
import com.dstz.springboot.autoconfigure.cache.enums.AbCacheTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ab 缓存属性配置
 *
 * @author wacxhs
 */
@ConfigurationProperties(prefix = "ab.cache")
public class AbCacheProperties {

    /**
     * 缓存类型
     */
    private AbCacheTypeEnum type = AbCacheTypeEnum.MEMORY;

    /**
     * 缓存区域，region=缓存时间[,缓存大小]
     */
    private Map<String, String> region;

    public AbCacheTypeEnum getType() {
        return type;
    }

    public void setType(AbCacheTypeEnum type) {
        this.type = type;
    }

    public Map<String, String> getRegion() {
        return region;
    }

    public void setRegion(Map<String, String> region) {
        this.region = region;
    }

    public List<CacheRegion> getCacheRegionList() {
        List<CacheRegion> cacheRegionList = new ArrayList<>();
        if (MapUtil.isNotEmpty(region)) {
            for (Map.Entry<String, String> entry : region.entrySet()) {
                Duration expire;
                Long size = null;
                int delimiterIndex = entry.getValue().indexOf(CharUtil.COMMA);
                if (delimiterIndex == StrUtil.INDEX_NOT_FOUND) {
                    expire = Duration.parse(entry.getValue().trim());
                } else {
                    expire = Duration.parse(entry.getValue().substring(0, delimiterIndex).trim());
                    size = Long.parseLong(entry.getValue().substring(delimiterIndex + 1).trim());
                }
                cacheRegionList.add(new CacheRegion(entry.getKey(), expire, size));
            }
        }
        return cacheRegionList;
    }
}
