package com.dstz.component.mq.engine.constants;

import com.dstz.base.common.codes.IBaseCode;

/**
 * 消息队列名称
 *
 * @author lightning
 */
public enum MqExceptionCodeConstant implements IBaseCode {

    SEND_ERROR("send_error", "消息发送失败,{}"),
    ;

    private final String code;

    private final String message;

    MqExceptionCodeConstant(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
