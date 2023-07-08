package com.dstz.component.mq.engine.consumer;

import com.dstz.component.mq.api.JmsHandler;
import com.dstz.component.mq.api.model.JmsDTO;
import org.springframework.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;

/**
 * 通用消息队列消费
 *
 * @author lightning
 */
public class CommonMessageQueueConsumer extends AbstractMessageQueue {


    private static final Logger LOGGER = LoggerFactory.getLogger(CommonMessageQueueConsumer.class);

    /**
     * 加载完成
     */
    private volatile boolean loadComplete;

    private final byte[] monitor = new byte[0];

    @Override
    protected void containerInitialCompleteAfter() {
        this.loadComplete = true;
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    /**
     * 处理消息
     *
     * @param jmsDTO jmsDTO
     */
    public void handleMessage(JmsDTO<Serializable> jmsDTO) {
        //防止还未加载成功，消息已经调用
        while (!this.loadComplete) {
            synchronized (monitor) {
                try {
                    monitor.wait(500L);
                } catch (InterruptedException e) {
                    LOGGER.error("Interrupted!", e);
                    Thread.currentThread().interrupt();
                    ReflectionUtils.rethrowRuntimeException(e);
                }
            }
        }
        JmsHandler<Serializable> jmsHandler = getJmsHandler(jmsDTO.getType());
        if (jmsHandler == null) {
            LOGGER.warn("{} no handler", jmsDTO.getType());
            return;
        }
        jmsHandler.handlerMessage(jmsDTO);
    }
}
