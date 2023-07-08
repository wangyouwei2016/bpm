package com.dstz.cms.api.model;

import com.dstz.base.common.utils.UserContextUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 替换模版内数据的实体类
 *
 * @author niu
 * @since 2022-03-10
 */

public class FreemarkerData {

    /**
     * 标题 (新闻/行程/公告/流程的名称)
     */
    private String subject;

    /**
     * 发表的意见(流程模块的待办/驳回需要填写)
     */
    private String submitOpinion;

    /**
     * 上一节点名称 (流程驳回需要填写)
     */
    private String submitTaskname;

    /**
     * 类型(中文:新闻/行程/公告/流程)
     */
    private String type;

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~下方属性可以忽略不填~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    /**
     * 发送人(自动填充,可以省略不写)
     */
    private String senderName = UserContextUtils.getUser().get().getFullName();

    /**
     * 发送时间(自动填充,可以省略不写)
     */
    private String sendTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSubmitOpinion() {
        return submitOpinion;
    }

    public void setSubmitOpinion(String submitOpinion) {
        this.submitOpinion = submitOpinion;
    }

    public String getSubmitTaskname() {
        return submitTaskname;
    }

    public void setSubmitTaskname(String submitTaskname) {
        this.submitTaskname = submitTaskname;
    }

    /**
     * 供流程快速创建使用(流程待办,流程驳回使用,  submitTaskname属性为驳回时所需, 待办可以传null)
     */
    public FreemarkerData(String subject, String submitOpinion, String submitTaskname) {
        this.subject = subject;
        this.submitOpinion = submitOpinion;
        this.submitTaskname = submitTaskname;
    }

    /**
     * 快速创建构造对象(新闻,公告,行程,流程结束使用)
     */
    public FreemarkerData(String subject) {
        this.subject = subject;
    }

    public FreemarkerData() {
    }

}
