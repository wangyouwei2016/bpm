package com.dstz.sys.rest.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jinxia.hou
 * @Name ParticipantScheduleVo
 * @description:
 * @date 2022/2/1816:05
 */
public class ParticipantScheduleVo implements Serializable {

    private static final long serialVersionUID = -6592562315091674625L;
    /**
     * ID
     */
    protected String id;
    /**
     * 业务关联id。此id用在回调时
     */
    protected String bizId;

    /**
     * 标题
     */
    protected String title;

    /**
     * 描述
     */
    protected String remark;

    /**
     * 任务连接
     */
    protected String taskUrl;

    /**
     * 类型
     */
    protected String type;

    /**
     * 任务打开方式
     */
    protected String openType;

    /**
     * 所属人
     */
    protected String owner;

    /**
     * 所属人
     */
    protected String ownerName;

    /**
     * 参与者
     */
    protected String participantNames;

    /**
     * 开始日期
     */
    protected java.util.Date startTime;

    /**
     * 结束日期
     */
    protected java.util.Date endTime;

    /**
     * 实际开始日期
     */
    protected java.util.Date actualStartTime;

    /**
     * 完成时间
     */
    protected java.util.Date completeTime;

    /**
     * 进度
     */
    protected Integer rateProgress;

    /**
     * 提交人
     */
    protected String submitter;

    /**
     * 提交人
     */
    protected String submitterName;

    /**
     * isLock
     */
    protected String isLock;

    /**
     * 创建时间
     */
    protected java.util.Date createTime;

    /**
     * 创建人
     */
    protected String createBy;

    /**
     * 更新时间
     */
    protected java.util.Date updateTime;

    /**
     * 更新人
     */
    protected String updateBy;

    /**
     * 删除标记
     */
    protected String deleteFlag;


    /**
     * 日程ID
     */
    protected String scheduleId;

    /**
     * 参与者名字
     */
    protected String participantorName;

    /**
     * 参与者
     */
    protected String participantor;


    /**
     * ilka提交注释
     */
    protected String submitComment;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getParticipantNames() {
        return participantNames;
    }

    public void setParticipantNames(String participantNames) {
        this.participantNames = participantNames;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getRateProgress() {
        return rateProgress;
    }

    public void setRateProgress(Integer rateProgress) {
        this.rateProgress = rateProgress;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getParticipantorName() {
        return participantorName;
    }

    public void setParticipantorName(String participantorName) {
        this.participantorName = participantorName;
    }

    public String getParticipantor() {
        return participantor;
    }

    public void setParticipantor(String participantor) {
        this.participantor = participantor;
    }

    public String getSubmitComment() {
        return submitComment;
    }

    public void setSubmitComment(String submitComment) {
        this.submitComment = submitComment;
    }
}
