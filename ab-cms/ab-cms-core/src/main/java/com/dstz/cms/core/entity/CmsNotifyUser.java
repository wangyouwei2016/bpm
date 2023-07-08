package com.dstz.cms.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dstz.base.entity.AbModel;

import java.util.Date;

/**
 * 公告用户关联对象和
 *
 * @author niu
 * @since 2022-02-28
 */
@TableName("cms_notify_user")
public class CmsNotifyUser extends AbModel<CmsNotifyUser> {

    /**
     * 主键
     */
    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公告id
     */
    @TableField("notify_id_")
    private String notifyId;

    /**
     * 用户id
     */
    @TableField("user_id_")
    private String userId;

    /**
     * (创建时间)阅读时间
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

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CmsNotifyUser(String notifyId, String userId) {
        this.notifyId = notifyId;
        this.userId = userId;
    }
}
