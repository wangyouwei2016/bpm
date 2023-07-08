package com.dstz.cms.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dstz.base.common.valuemap.AbValueMap;
import com.dstz.base.common.valuemap.AbValueMapType;
import com.dstz.base.entity.AbModel;

import java.util.Date;

/**
 * 系统公告表
 *
 * @author niu
 * @since 2022-02-28
 */
@TableName("cms_notify")
public class CmsNotify extends AbModel<CmsNotify> {

    /**
     * 主键
     */
    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公告标题
     */
    @TableField("title_")
    private String title;

    /**
     * 公告内容
     */
    @TableField("content_")
    private String content;

    /**
     * 公告类型code
     */
    @AbValueMap(type = AbValueMapType.DICT, fixValue = "gglx", matchField = "code", attrMap = @AbValueMap.AttrMap(originName = "name"))
    @TableField("type_id_")
    private String typeId;

    /**
     * 附件json格式字符串信息
     */
    @TableField("attachments_")
    private String attachments;

    /**
     * 发布人或下架人根据状态来区分
     */
    @TableField("release_by_")
    private String releaseBy;

    /**
     * 发布人姓名
     */
    @TableField("release_name_")
    private String releaseName;

    /**
     * 发布时间或下架时间根据状态来区分
     */
    @TableField("release_time_")
    private Date releaseTime;

    /**
     * 发布状态0未发布1已发布2下架
     */
    @TableField("status_")
    private Integer status;

    /**
     * 评论数量
     */
    @TableField("comments_num_")
    private int commentsNum;

    /**
     * 访问数量
     */
    @TableField("visit_num_")
    private int visitNum;

    /**
     * 创建时间
     */
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人ID
     */
    @TableField(value = "create_by_", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 所属组织
     */
    @TableField(value = "create_org_id_", fill = FieldFill.INSERT)
    private String createOrgId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time_", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "updater_", fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 更新人ID
     */
    @TableField(value = "update_by_", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    public CmsNotify(String id, String title, String content, String typeId, String attachments, String releaseBy, String releaseName, Date releaseTime, Integer status, int commentsNum, int visitNum, Date createTime, String createBy, String createOrgId, Date updateTime, String updater, String updateBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.typeId = typeId;
        this.attachments = attachments;
        this.releaseBy = releaseBy;
        this.releaseName = releaseName;
        this.releaseTime = releaseTime;
        this.status = status;
        this.commentsNum = commentsNum;
        this.visitNum = visitNum;
        this.createTime = createTime;
        this.createBy = createBy;
        this.createOrgId = createOrgId;
        this.updateTime = updateTime;
        this.updater = updater;
        this.updateBy = updateBy;
    }

    public CmsNotify() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void releaseNotify(String userId, String fullName) {
        this.releaseBy = userId;
        this.releaseName = fullName;
        this.status = 1;
        this.releaseTime = new Date();
    }

    public void withdrawNotify(String userId, String fullName) {
        this.releaseBy = userId;
        this.releaseName = fullName;
        this.status = 2;
        this.releaseTime = new Date();
    }



    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getAttachments() {
        return attachments;
    }

    public String getReleaseBy() {
        return releaseBy;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public Integer getStatus() {
        return status;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public int getVisitNum() {
        return visitNum;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    public String getCreateOrgId() {
        return createOrgId;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public String getUpdater() {
        return updater;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public void setReleaseBy(String releaseBy) {
        this.releaseBy = releaseBy;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }
    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setUpdater(String updater) {
        this.updater = updater;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
