package com.dstz.component.mq.engine.producer;

import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.component.mq.engine.constants.MqExceptionCodeConstant;
import com.dstz.component.mq.engine.consumer.AbstractMessageQueue;
import com.dstz.component.mq.api.JmsHandler;
import com.dstz.component.mq.api.model.JmsDTO;
import com.dstz.component.mq.api.producer.JmsProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 同步队列发送，内部应用
 *
 * @author lightning
 */
public class SynchronousQueueProducer extends AbstractMessageQueue implements JmsProducer {

    private static final Logger logger = LoggerFactory.getLogger(SynchronousQueueProducer.class);

    @SuppressWarnings("unchecked")
    @Override
    public void sendToQueue(JmsDTO message) {
        try {
            JmsHandler jmsHandler = getJmsHandler(message.getType());
            if (jmsHandler == null) {
                logger.warn("{} handler did not find a processing implementation", message.getType());
                return;
            }
            jmsHandler.handlerMessage(message);
        } catch (Exception e) {
            throw new BusinessException(MqExceptionCodeConstant.SEND_ERROR.formatMessage(e.getMessage()));
        }
    }

    @Override
    public void sendToQueue(List<JmsDTO> messages) {
        try {
            if (messages != null && !messages.isEmpty()) {
                for (JmsDTO jmsDTO : messages) {
                    sendToQueue(jmsDTO);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(MqExceptionCodeConstant.SEND_ERROR, e);
        }
    }
}
