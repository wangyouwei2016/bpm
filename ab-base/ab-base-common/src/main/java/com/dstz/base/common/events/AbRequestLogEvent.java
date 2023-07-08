package com.dstz.base.common.events;

import com.dstz.base.common.requestlog.AbRequestLog;
import com.dstz.base.common.utils.ToStringUtils;
import org.springframework.context.ApplicationEvent;

/**
 * 请求日志事件
 *
 * @author wacxhs
 */
public class AbRequestLogEvent extends ApplicationEvent {

    private static final long serialVersionUID = 2467689461606762863L;

    /**
     * 事件类型
     */
    public enum EventType {
        /**
         *
         */
        PRE_PROCESS,

        /**
         * 请求处理后
         */
        POST_PROCESS
    }

    private final EventType eventType;


    private AbRequestLogEvent(AbRequestLog abRequestLog, EventType eventType) {
        super(abRequestLog);
        this.eventType = eventType;
    }

    /**
     * 创建请求处理前事件
     *
     * @param abRequestLog 请求日志
     * @return 请求日志事件
     */
    public static AbRequestLogEvent createPreProcess(AbRequestLog abRequestLog) {
        return new AbRequestLogEvent(abRequestLog, EventType.PRE_PROCESS);
    }

    /**
     * 创建请求处理后置事件
     *
     * @param abRequestLog 请求日志
     * @return 请求日志事件
     */
    public static AbRequestLogEvent createPostProcess(AbRequestLog abRequestLog) {
        return new AbRequestLogEvent(abRequestLog, EventType.POST_PROCESS);
    }

    /**
     * 获取事件类型
     *
     * @return 事件类型
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * 获取请求日志
     *
     * @return 请求日志
     */
    public AbRequestLog getRequestLog() {
        return (AbRequestLog) getSource();
    }

    @Override
    public String toString() {
        return ToStringUtils.toString(this);
    }
}
