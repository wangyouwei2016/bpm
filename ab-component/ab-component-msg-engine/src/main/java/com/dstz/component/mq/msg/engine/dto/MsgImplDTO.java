package com.dstz.component.mq.msg.engine.dto;

import com.dstz.base.common.identityconvert.SysIdentity;
import com.dstz.component.msg.api.vo.CardTemplateData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模板消息业务实现dto
 * </p>
 *
 * @author lightning
 * @since 2022-11-21
 */
public class MsgImplDTO implements Serializable {
    //标题
    private String subject;

    //模板参数
    private Map<String,String> templateParam;

    //html模板
    private String htmlTemplate;

    //卡片模板
    private CardTemplateData cardTemplateData;

    //接收者
    private List<SysIdentity> receivers;

    //业务id
    private String businessId;

    //业务类型 参考com.dstz.base.common.constats.InnerMsgEnum
    private String innerMsgType;

    //扩展参数
    private Map<String, Object> extendVars = new HashMap<String, Object>();

    public MsgImplDTO() {
    }

    public MsgImplDTO(String subject,String htmlTemplate, List<SysIdentity> receivers) {
        this.subject = subject;
        this.htmlTemplate = htmlTemplate;
        this.receivers = receivers;
    }

    public MsgImplDTO(String subject,Map<String,String> templateParam, String htmlTemplate, CardTemplateData cardTemplateData, List<SysIdentity> receivers, String businessId, String innerMsgType, Map<String, Object> extendVars) {
        this.subject = subject;
        this.templateParam = templateParam;
        this.htmlTemplate = htmlTemplate;
        this.cardTemplateData = cardTemplateData;
        this.receivers = receivers;
        this.businessId = businessId;
        this.innerMsgType = innerMsgType;
        this.extendVars = extendVars;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, String> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(Map<String, String> templateParam) {
        this.templateParam = templateParam;
    }

    public String getHtmlTemplate() {
        return htmlTemplate;
    }

    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    public CardTemplateData getCardTemplateData() {
        return cardTemplateData;
    }

    public void setCardTemplateData(CardTemplateData cardTemplateData) {
        this.cardTemplateData = cardTemplateData;
    }

    public List<SysIdentity> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<SysIdentity> receivers) {
        this.receivers = receivers;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getInnerMsgType() {
        return innerMsgType;
    }

    public void setInnerMsgType(String innerMsgType) {
        this.innerMsgType = innerMsgType;
    }

    public Map<String, Object> getExtendVars() {
        return extendVars;
    }

    public void setExtendVars(Map<String, Object> extendVars) {
        this.extendVars = extendVars;
    }
}
