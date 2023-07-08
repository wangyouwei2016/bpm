package com.dstz.component.mq.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息队列配置
 *
 * @author lightning
 */
@ConfigurationProperties(prefix = "ab.simple-mq")
public class AbSimpleMessageQueueProperties {

    /**
     * 消息队列方式
     */
    private AbMessageQueueType messageQueueType = AbMessageQueueType.SYNCHRONOUS;

    public AbMessageQueueType getMessageQueueType() {
        return messageQueueType;
    }

    public void setMessageQueueType(AbMessageQueueType messageQueueType) {
        this.messageQueueType = messageQueueType;
    }
}
