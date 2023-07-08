package com.dstz.groovy.script.engine.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * groovy 脚本属性配置
 *
 * @author wacxhs
 */
@ConfigurationProperties(prefix = "ab.groovy")
public class AbGroovyScriptProperties {

    /**
     * 编译缓存
     */
    private Integer compileCache = 20;

    public Integer getCompileCache() {
        return compileCache;
    }

    public void setCompileCache(Integer compileCache) {
        this.compileCache = compileCache;
    }
}
