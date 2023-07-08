package com.dstz.component.mq.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis消息队列消费属性配置
 *
 * @author lightning
 */
@ConfigurationProperties(prefix = "ab.simple-mq.redis-consumer")
public class AbRedisMessageQueueConsumerProperties {

    /**
     * redisTemplate 容器中名称
     */
    private String redisTemplateBeanName = "redisTemplate";

    public String getRedisTemplateBeanName() {
        return redisTemplateBeanName;
    }

    public void setRedisTemplateBeanName(String redisTemplateBeanName) {
        this.redisTemplateBeanName = redisTemplateBeanName;
    }
}
