package com.dstz.component.mq.msg.engine.constants;

import com.dstz.base.common.codes.IBaseCode;

/**
 * msg实现相关响应码
 *
 * @author lightning
 */
public enum MsgEngineStatusCode implements IBaseCode {



    PARAM_TEMPLATE_CODE_MISS("param_template_code_miss","模板code参数缺失"),
    GET_DATA_BY_CODE_IS_NULL("get_data_by_code_is_null","根据code未查到消息模板数据code：{}"),

    EMAIL_SEND_ERROR("email_send_error", "邮件发送失败 {}"),


    ;


    private final String code;
    private final String desc;

    MsgEngineStatusCode(String code, String description) {
        this.code = code;
        this.desc = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return desc;
    }
}
