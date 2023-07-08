package com.dstz.cms.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dstz.base.entity.AbModel;

import java.util.Date;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author niu
 * @since 2022-03-04
 */
@TableName("cms_comments")
public class CmsComments extends AbModel<CmsComments> {

    /**
     * 评论纪录主键
     */
    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公告或新闻纪录主键
     */
    @TableField("msg_id_")
    private String msgId;

    /**
     * 0:公告1:新闻
     */
    @TableField("comment_type_")
    private int commentType;

    /**
     * 评论内容
     */
    @TableField("comment_content_")
    private String commentContent;

    /**
     * 评论者主键
     */
    @TableField(value = "create_by_", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 评论人姓名
     */

    @TableField(value = "creator_", fill = FieldFill.INSERT)
    private String creator;

    /**
     * 评论发表时间
     */
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
