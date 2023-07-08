package com.dstz.component.mq.engine.config;

import com.dstz.component.mq.engine.consumer.RedisMessageQueueConsumer;
import com.dstz.component.mq.api.producer.JmsProducer;
import com.dstz.component.mq.engine.producer.RedisMessageQueueProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis 消息队列自动装配
 *
 * @author lightning
 */
@Conditional(AbMessageQueueConditional.class)
@ConditionalOnClass(RedisTemplate.class)
@EnableConfigurationProperties(AbRedisMessageQueueConsumerProperties.class)
public class AbRedisMessageQueueConfiguration {

    @Bean
    public JmsProducer jmsProducer() {
        return new RedisMessageQueueProducer();
    }

    @Bean
    public RedisMessageQueueConsumer redisMessageQueueConsumer(AbRedisMessageQueueConsumerProperties properties) {
        RedisMessageQueueConsumer redisMessageQueueConsumer = new RedisMessageQueueConsumer();
        redisMessageQueueConsumer.setRedisTemplateBeanName(properties.getRedisTemplateBeanName());
        return redisMessageQueueConsumer;
    }

}
