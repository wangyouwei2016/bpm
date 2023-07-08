package com.dstz.org.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.valuemap.AbValueMap;
import com.dstz.base.common.valuemap.AbValueMapType;
import com.dstz.base.entity.AbModel;
import com.dstz.org.enums.OrgStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author xz
 * @since 2022-02-07
 */
@TableName("org_role")
public class Role extends AbModel<Role> {

    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 角色名称
     */
    @TableField("name_")
    private String name;

    /**
     * 编码
     */
    @TableField("code_")
    private String code;

    /**
     * 0：禁用，1：启用
     */
    @AbValueMap(type = AbValueMapType.ENUM, fixClass = OrgStatus.class, matchField = "status",
            attrMap = {@AbValueMap.AttrMap(originName = "desc", targetName = "statusDesc"),
                    @AbValueMap.AttrMap(originName = "labelCss", targetName = "statusCss")
            })
    @TableField("enabled_")
    private Integer enabled = NumberPool.INTEGER_ONE;

    /**
     * 角色等级
     */
    @TableField("level_")
    private Integer level;

    /**
     * 描述
     */
    @TableField("desc_")
    private String desc = StrPool.EMPTY;

    /**
     * 分类字典编码
     */
    @AbValueMap(type = AbValueMapType.TYPE, fixValue = "jsfl", attrMap = {@AbValueMap.AttrMap(originName = "name", targetName = "typeName")})
    @TableField("type_code_")
    private String typeCode;

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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getUpdater() {
        return updater;
    }

    @Override
    public void setUpdater(String updater) {
        this.updater = updater;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
