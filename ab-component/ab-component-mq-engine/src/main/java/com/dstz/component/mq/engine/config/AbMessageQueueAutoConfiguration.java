package com.dstz.component.mq.engine.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 缓存相关配置
 *
 * @author lightning
 */
@Configuration
@Import(AbMessageQueueAutoConfiguration.AbCacheConfigurationSelector.class)
@EnableConfigurationProperties({ AbSimpleMessageQueueProperties.class})
public class AbMessageQueueAutoConfiguration {


    public static class AbCacheConfigurationSelector implements ImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            AbMessageQueueType[] types = AbMessageQueueType.values();
            String[] imports = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                imports[i] = types[i].getConfigurationClass().getName();
            }
            return imports;
        }
    }

}
