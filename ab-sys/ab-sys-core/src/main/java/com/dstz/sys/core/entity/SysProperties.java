package com.dstz.sys.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dstz.base.common.valuemap.AbValueMap;
import com.dstz.base.common.valuemap.AbValueMapType;
import com.dstz.base.entity.AbModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统属性
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-11
 */
@TableName("sys_properties")
public class SysProperties extends AbModel<SysProperties> {

    /**
     * ID
     */
    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 属性编码
     */
    @TableField("code_")
    private String code;

    /**
     * 属性名字
     */
    @TableField("name_")
    private String name;

    /**
     * 值
     */
    @TableField("value_")
    private String value;

    /**
     * 描述
     */
    @TableField("desc_")
    private String desc;

    /**
     * 是否加密
     */
    @TableField("encrypt_")
    private Integer encrypt;

    /**
     * 环境参数
     */
    @TableField("environment_")
    @AbValueMap(type = AbValueMapType.DICT, fixValue = "environment", matchField = "code", attrMap = @AbValueMap.AttrMap(originName = "name"))
    private String environment;

    /**
     * 分组
     */
    @TableField("type_code_")
   // @AbValueMap(type = AbValueMapType.DICT, fixValue = "property", matchField = "code", attrMap = @AbValueMap.AttrMap(originName = "name"))
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
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public Integer getEncrypt() {
        return encrypt;
    }
    
    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }
    
    public String getEnvironment() {
        return environment;
    }
    
    public void setEnvironment(String environment) {
        this.environment = environment;
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
