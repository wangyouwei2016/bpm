package com.dstz.component.mq.msg.engine.constants;

/**
 * 系统缓存key定义
 *
 * @author lightning
 * @since 2022-08-17
 */
public class MsgCackeKeyConstant {

    /**
     * 消息模板方法缓存key SpEL表达式
     */
    public static final String GET_MESSAGE_TEMPLATE_LIST = "'getMessageTemplateListEl:'";

    /**
     * 字典方法缓存key SpEL表达式
     */
    public static final String ET_MESSAGE_TEMPLATE_LIST_RECEIVE_EL = GET_MESSAGE_TEMPLATE_LIST + ".concat(#root.args[0])";

}
