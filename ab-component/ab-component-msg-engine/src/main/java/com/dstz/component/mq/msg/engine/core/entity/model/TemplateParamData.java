package com.dstz.component.mq.msg.engine.core.entity.model;

/**
 * <p>
 * 消息模板dto
 * </p>
 *
 * @author lightning
 * @since 2022-11-17
 */
public class TemplateParamData {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
