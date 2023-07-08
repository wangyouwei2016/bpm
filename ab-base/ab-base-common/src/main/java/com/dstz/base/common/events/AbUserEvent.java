package com.dstz.base.common.events;

import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.Objects;

/**
 * 用户更新缓存事件event
 *
 * @author lightning
 */
public class AbUserEvent extends ApplicationEvent {
    public AbUserEvent(List<String> userAccount, EventType eventType) {
        super(userAccount);
        this.eventType = Objects.requireNonNull(eventType);
    }

    /**
     * 事件类型
     */
    public enum EventType {
        /**
         * 更新用户
         */
        UPDATE_USER,

        /**
         * 删除用户
         */
        DELETE_USER,


    }

    private final EventType eventType;

    public List<String> getUserAccountList() {
        return (List<String>) source;
    }

    public EventType getEventType() {
        return eventType;
    }
}
