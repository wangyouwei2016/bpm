package com.dstz.component.msg.api.dto;

import com.dstz.base.common.identityconvert.SysIdentity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模板消息dto
 * </p>
 *
 * @author lightning
 * @since 2022-11-21
 */
public class MsgDTO implements Serializable {
    //标题
    private String subject;

    //模板key
    private String templateCode;

    //接收者
    private List<SysIdentity> receivers;

    //业务id
    private String businessId;

    //业务类型 参考com.dstz.base.common.constats.InnerMsgEnum
    private String innerMsgType;

    //发送消息类型, 消息具体实现如email
    private List<String> msgType;

    //格式化所需系统参数
    private Object object;

    //扩展参数
    private Map<String, Object> extendVars = new HashMap<String, Object>();

    public MsgDTO() {
    }

    public MsgDTO(String subject, String templateCode,  List<SysIdentity> receivers, List<String> msgType, Object object) {
        this.subject = subject;
        this.templateCode = templateCode;
        this.receivers = receivers;
        this.msgType = msgType;
        this.object = object;
    }


    public MsgDTO(String subject, String templateCode,  List<SysIdentity> receivers, String businessId, String innerMsgType, List<String> msgType, Object object) {
        this.subject = subject;
        this.templateCode = templateCode;
        this.receivers = receivers;
        this.businessId = businessId;
        this.innerMsgType = innerMsgType;
        this.msgType = msgType;
        this.object = object;
    }

    public MsgDTO(String subject, String templateCode,  List<SysIdentity> receivers, String businessId, String innerMsgType, List<String> msgType, Object object, Map<String, Object> extendVars) {
        this.subject = subject;
        this.templateCode = templateCode;
        this.receivers = receivers;
        this.businessId = businessId;
        this.innerMsgType = innerMsgType;
        this.msgType = msgType;
        this.object = object;
        this.extendVars = extendVars;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
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

    public List<String> getMsgType() {
        return msgType;
    }

    public void setMsgType(List<String> msgType) {
        this.msgType = msgType;
    }
}
