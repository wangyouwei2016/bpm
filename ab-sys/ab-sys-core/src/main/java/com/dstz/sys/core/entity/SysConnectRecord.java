package com.dstz.sys.core.entity;

import com.dstz.base.entity.IPersistentEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 公共业务关联记录
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@TableName("sys_connect_record")
public class SysConnectRecord extends Model<SysConnectRecord> implements IPersistentEntity {

    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 类型
     */
    @TableField("type_")
    private String type;

    /**
     * 源ID
     */
    @TableField("source_id_")
    private String sourceId;

    /**
     * 关联ID
     */
    @TableField("target_id_")
    private String targetId;

    /**
     * 提示信息
     */
    @TableField("notice")
    private String notice;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysConnectRecord{" +
                "id=" + id +
                ", type=" + type +
                ", sourceId=" + sourceId +
                ", targetId=" + targetId +
                ", notice=" + notice +
                "}";
    }
}
