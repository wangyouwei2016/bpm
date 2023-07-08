package com.dstz.component.mq.msg.engine.core.manager.impl;

import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.component.mq.msg.engine.core.entity.MessageTemplate;
import com.dstz.component.mq.msg.engine.core.manager.MessageTemplateManager;
import org.springframework.stereotype.Service;

/**
 * 通用服务实现类
 *
 * @author lightning
 * @since 2022-11-14
 */
@Service("messageTemplateManager")
public class MessageTemplateManagerImpl extends AbBaseManagerImpl<MessageTemplate> implements MessageTemplateManager {

}
