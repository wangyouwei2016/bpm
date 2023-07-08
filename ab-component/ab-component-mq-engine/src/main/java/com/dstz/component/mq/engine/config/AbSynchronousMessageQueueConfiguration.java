package com.dstz.component.mq.engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import com.dstz.component.mq.api.producer.JmsProducer;
import com.dstz.component.mq.engine.producer.SynchronousQueueProducer;

/**
 * 同步消息队列配置
 *
 * @author lightning
 */
@Conditional(AbMessageQueueConditional.class)
public class AbSynchronousMessageQueueConfiguration {

    /**
     * 默认消息发送提供者
     *
     * @return 消息发送提供端
     */
    @Bean
    public JmsProducer jmsProducer() {
        return new SynchronousQueueProducer();
    }

}
