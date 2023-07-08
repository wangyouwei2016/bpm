package com.dstz.component.uploader.ordinary;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * Ordinary 属性配置
 *
 * @author lightning
 */
@ConfigurationProperties(prefix = "uploader.ordinary")
public class OrdinaryProperties {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
