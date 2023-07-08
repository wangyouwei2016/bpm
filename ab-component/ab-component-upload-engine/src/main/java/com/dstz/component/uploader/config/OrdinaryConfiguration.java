package com.dstz.component.uploader.config;

import com.dstz.component.uploader.ordinary.OrdinaryProperties;
import com.dstz.component.uploader.ordinary.OrdinaryUploader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 本地磁盘配置
 * @author lightning
 */
@Configuration
@EnableConfigurationProperties(OrdinaryProperties.class)
public class OrdinaryConfiguration {

    @Bean
    public OrdinaryUploader ordinaryUploader() {
        return new OrdinaryUploader();
    }
}
