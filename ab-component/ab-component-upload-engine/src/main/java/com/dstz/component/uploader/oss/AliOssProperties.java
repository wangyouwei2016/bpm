package com.dstz.component.uploader.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * oss 属性配置
 *
 * @author lightning
 */
@ConfigurationProperties(prefix = "spring.cloud.alicloud.oss")
public class AliOssProperties {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * bucket name
     */

    private String bucketName;

    /**
     * base dir
     */
    private String baseDir;

    public String getBucketName() {
        return bucketName;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }
}
