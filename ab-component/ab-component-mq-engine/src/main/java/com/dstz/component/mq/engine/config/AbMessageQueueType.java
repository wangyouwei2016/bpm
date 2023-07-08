package com.dstz.component.mq.engine.config;

/**
 * 消息队列类型
 *
 * @author lightning
 */
public enum AbMessageQueueType {

    /**
     * redis
     */
    REDIS(AbRedisMessageQueueConfiguration.class),

    /**
     * java消息队列
     */
    JMS(AbMessageQueueConfiguration.class),

    /**
     * 同步方式
     */
    SYNCHRONOUS(AbSynchronousMessageQueueConfiguration.class),

    /**
     * 事务消息
     */
   // TRANSACTION_MESSAGE(TxmMessageQueueConfiguration.class),

    /**
     * 丢弃消息
     */
    DISCARD(DiscardMessageQueueConfiguration.class);

    /**
     * 配置类
     */
    private Class configurationClass;

    AbMessageQueueType(Class configurationClass) {
        this.configurationClass = configurationClass;
    }

    public Class getConfigurationClass() {
        return configurationClass;
    }
}
