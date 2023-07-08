package com.dstz.sys.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dstz.base.entity.AbModel;

import java.util.Date;

/**
 * <p>
 * 用户常用语
 * </p>
 *
 * @author niu
 * @since 2022-03-14
 */
@TableName("sys_daily_phrases")
public class SysDailyPhrases extends AbModel<SysDailyPhrases> {

    /**
     * 主键id非空
     */
    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 常用语文本
     */
    @TableField("locution_")
    private String locution;

    /**
     * 是否启用
     */
    @TableField("enable_")
    private Integer enable;

    /**
     * 是否默认 0自定义 1默认
     */
    @TableField("is_default_")
    private Integer isDefault = 0;

    /**
     * 创建者
     */
    @TableField(value = "create_by_", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建者姓名
     */
    @TableField(value = "creator_", fill = FieldFill.INSERT)
    private String creator;

    /**
     * 创建时间
     */
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time_", fill = FieldFill.UPDATE)
    private Date updateTime;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getLocution() {
        return locution;
    }

    public void setLocution(String locution) {
        this.locution = locution;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
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

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
