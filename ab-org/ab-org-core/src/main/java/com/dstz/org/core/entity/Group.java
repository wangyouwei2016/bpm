package com.dstz.org.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dstz.base.common.valuemap.AbValueMap;
import com.dstz.base.common.valuemap.AbValueMapType;
import com.dstz.base.entity.AbModel;
import com.dstz.org.api.enums.GroupGradeConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 组织架构
 * </p>
 *
 * @author xz
 * @since 2022-02-07
 */
@TableName("org_group")
public class Group extends AbModel<Group> {

    /**
     * 主键
     */
    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    protected String id;

    /**
     * 名字
     */
    @TableField("name_")
    protected String name;

    /**
     * 父ID
     */
    @TableField("parent_id_")
    protected String parentId;

    /**
     * 排序
     */
    @TableField("sn_")
    protected Integer sn;

    @TableField("code_")
    protected String code;

    /**
     * 类型：0集团，1公司，3部门
     */
    @AbValueMap(type = AbValueMapType.ENUM, fixClass = GroupGradeConstant.class, matchField = "key", attrMap = {@AbValueMap.AttrMap(originName = "label", targetName = "groupType")})
    @TableField("type_")
    protected String type;

    /**
     * 描述
     */
    @TableField("desc_")
    protected String desc;

    /**
     * 组织路径
     */
    @TableField("path_")
    protected String path;

    /**
     * 组织路径
     */
    @TableField("path_name_")
    protected String pathName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 创建人ID
     */
    @TableField(value = "create_by_", fill = FieldFill.INSERT)
    protected String createBy;

    /**
     * 所属组织
     */
    @TableField(value = "create_org_id_", fill = FieldFill.INSERT)
    protected String createOrgId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time_", fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "updater_", fill = FieldFill.INSERT_UPDATE)
    protected String updater;

    /**
     * 更新人ID
     */
    @TableField(value = "update_by_", fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
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
