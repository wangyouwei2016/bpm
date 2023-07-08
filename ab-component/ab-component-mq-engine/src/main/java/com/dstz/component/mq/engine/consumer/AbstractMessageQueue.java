package com.dstz.component.mq.engine.consumer;

import com.dstz.component.mq.api.JmsHandler;
import com.dstz.component.mq.engine.constants.MqEngineConstant;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象公共消息队列
 *
 * @author lightning
 */
public abstract class AbstractMessageQueue implements ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;

    /**
     * 注册消息处理器
     */
    private Map<String, JmsHandler<Serializable>> registerJmsHandler = Collections.emptyMap();

    @SuppressWarnings("unchecked")
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null || MqEngineConstant.APPLICATION_CONTEXT_EVENT_ID.equals(event.getApplicationContext().getParent().getId())) {
            this.applicationContext = event.getApplicationContext();
            this.registerJmsHandler = applicationContext.getBeansOfType(JmsHandler.class).values().stream().collect(Collectors.toMap(JmsHandler::getType, o -> o));
            containerInitialCompleteAfter();
        }
    }

    /**
     * 容器初始化完成后
     */
    protected void containerInitialCompleteAfter() {
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取消息处理实现
     *
     * @return 消息处理实现
     */
    protected JmsHandler<Serializable> getJmsHandler(String type) {
        return this.registerJmsHandler.get(type);
    }
}
