package com.dstz.springboot.autoconfigure.web.requestlog;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * 请求日志属性配置
 *
 * @author wacxhs
 */
@ConfigurationProperties(prefix = "ab.request-log")
public class AbRequestLogProperties {

    /**
     * 包含请求头名称，不配置则包含所有
     */
    private Set<String> includeHeaderNames;

    /**
     * 脱敏字段
     */
    private Set<String> sensitiveFields;

    /**
     * 排除地址
     */
    private String[] excludeUrls;

    /**
     * 启用输出
     */
    private boolean enableOutput = true;

    public Set<String> getIncludeHeaderNames() {
        return includeHeaderNames;
    }

    public void setIncludeHeaderNames(Set<String> includeHeaderNames) {
        this.includeHeaderNames = includeHeaderNames;
    }

    public Set<String> getSensitiveFields() {
        return sensitiveFields;
    }

    public void setSensitiveFields(Set<String> sensitiveFields) {
        this.sensitiveFields = sensitiveFields;
    }

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public boolean isEnableOutput() {
        return enableOutput;
    }

    public void setEnableOutput(boolean enableOutput) {
        this.enableOutput = enableOutput;
    }
}
