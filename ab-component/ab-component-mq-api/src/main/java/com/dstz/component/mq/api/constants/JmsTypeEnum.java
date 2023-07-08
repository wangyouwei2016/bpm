package com.dstz.component.mq.api.constants;

/**
 * 发送消息类型枚举
 */
public enum JmsTypeEnum {
    INNER("inner"),
    EMAIL("email"),
    DING_DING("dingding"),
    SMS("sms"),
    WEI_XIN("weixin"),
    WEI_XIN_QY("weixinQy")
    ;

    private final String type;

    JmsTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
