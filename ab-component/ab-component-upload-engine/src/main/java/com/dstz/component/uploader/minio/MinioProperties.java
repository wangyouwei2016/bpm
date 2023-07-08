package com.dstz.component.uploader.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 * minio属性配置
 * </pre>
 *
 * @author lightning
 */
@ConfigurationProperties(prefix = "ab.minio")
public class MinioProperties {

    private String endpoint;


    private String accessKey;


    private String secretKey;

    private String bucketName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
