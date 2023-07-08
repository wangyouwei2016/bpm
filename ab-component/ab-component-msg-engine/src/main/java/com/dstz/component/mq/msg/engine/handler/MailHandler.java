
package com.dstz.component.mq.msg.engine.handler;

import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.base.common.identityconvert.IdentityConvert;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.component.mq.msg.engine.dto.MsgImplDTO;
import com.dstz.component.mq.msg.engine.util.EmailUtil;
import com.dstz.component.third.msg.engine.handler.AbsNotifyMessageHandler;
import com.dstz.org.api.model.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.dstz.component.mq.api.constants.JmsTypeEnum.EMAIL;
import static com.dstz.component.mq.msg.engine.constants.MsgEngineStatusCode.EMAIL_SEND_ERROR;


/**
 * 邮件消息处理器。
 *
 * @author lightning
 */

@Component("mailHandler")
public class MailHandler extends AbsNotifyMessageHandler<MsgImplDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailHandler.class);
    @Autowired
    IdentityConvert identityConvert;
    
    @Override
    public String getType() {
        return EMAIL.getType();
    }


    @Override
    public String getTitle() {
        return "邮件";
    }

    @Override
    public boolean getIsDefault() {
        return true;
    }


    @Override
    public boolean getSupportHtml() {
        return true;
    }

    @Override
    public boolean sendMessage(MsgImplDTO notifMessage) {
        for (IUser reciver : identityConvert.convert2Users(notifMessage.getReceivers())) {
            String email = reciver.getAttrValue("email", String.class);
            if (StrUtil.isEmpty(email)) {
                continue;
            }
            //优先从普通文本框获取内容, 为空时再获取富文本
            String text = notifMessage.getHtmlTemplate();
            String content = text.replace(PARAM_USERNAME, reciver.getFullName());

            try {
                EmailUtil.send(email, notifMessage.getSubject(), content);
            } catch (Exception e) {
                LOGGER.error("发送邮件失败：{}， 发送参数：{}", e.getMessage(), JsonUtils.toJSONString(notifMessage), e);
                throw new BusinessException(EMAIL_SEND_ERROR.formatDefaultMessage(e.getMessage()));
            }
        }
        LOGGER.debug("发送邮件成功 ：{}", JsonUtils.toJSONString(notifMessage));
        return true;
    }

}

