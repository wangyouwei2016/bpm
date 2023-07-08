package com.dstz.component.mq.api.producer;

import com.dstz.component.mq.api.model.JmsDTO;

import java.util.List;

/**
 * 消息发送提供者
 *
 * @author lightning
 */
public interface JmsProducer {

    /**
     * 发送到队列中
     *
     * @param message 发送消息
     */
    void sendToQueue(JmsDTO message);

    /**
     * 发送列表到队列中
     *
     * @param messages 发送消息集
     */
    void sendToQueue(List<JmsDTO> messages);

}