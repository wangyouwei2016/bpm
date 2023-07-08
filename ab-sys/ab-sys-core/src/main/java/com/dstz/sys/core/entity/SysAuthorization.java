package com.dstz.sys.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dstz.base.entity.AbModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 通用资源授权配置
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@TableName("sys_authorization")
public class SysAuthorization extends AbModel<SysAuthorization> {

    /**
     * ID
     */
    @TableId(value = "rights_id_", type = IdType.ASSIGN_ID)
    private String rightsId;

    /**
     * 授权对象表分区用
     */
    @TableField("rights_object_")
    private String rightsObject;

    /**
     * 授权目标ID
     */
    @TableField("rights_target_")
    private String rightsTarget;

    /**
     * 权限类型
     */
    @TableField("rights_type_")
    private String rightsType;

    /**
     * 授权标识
     */
    @TableField("rights_identity_")
    private String rightsIdentity;

    /**
     * 标识名字
     */
    @TableField("rights_identity_name_")
    private String rightsIdentityName;

    /**
     * 授权code=identity+type
     */
    @TableField("rights_permission_code_")
    private String rightsPermissionCode;

    /**
     * 创建时间
     */
    @TableField("rights_create_time_")
    private Date rightsCreateTime;

    /**
     * 创建人
     */
    @TableField("rights_create_by_")
    private String rightsCreateBy;

    public String getRightsId() {
        return rightsId;
    }

    public void setRightsId(String rightsId) {
        this.rightsId = rightsId;
    }

    public String getRightsObject() {
        return rightsObject;
    }

    public void setRightsObject(String rightsObject) {
        this.rightsObject = rightsObject;
    }

    public String getRightsTarget() {
        return rightsTarget;
    }

    public void setRightsTarget(String rightsTarget) {
        this.rightsTarget = rightsTarget;
    }

    public String getRightsType() {
        return rightsType;
    }

    public void setRightsType(String rightsType) {
        this.rightsType = rightsType;
    }

    public String getRightsIdentity() {
        return rightsIdentity;
    }

    public void setRightsIdentity(String rightsIdentity) {
        this.rightsIdentity = rightsIdentity;
    }

    public String getRightsIdentityName() {
        return rightsIdentityName;
    }

    public void setRightsIdentityName(String rightsIdentityName) {
        this.rightsIdentityName = rightsIdentityName;
    }

    public String getRightsPermissionCode() {
        return rightsPermissionCode;
    }

    public void setRightsPermissionCode(String rightsPermissionCode) {
        this.rightsPermissionCode = rightsPermissionCode;
    }

    public Date getRightsCreateTime() {
        return rightsCreateTime;
    }

    public void setRightsCreateTime(Date rightsCreateTime) {
        this.rightsCreateTime = rightsCreateTime;
    }

    public String getRightsCreateBy() {
        return rightsCreateBy;
    }

    public void setRightsCreateBy(String rightsCreateBy) {
        this.rightsCreateBy = rightsCreateBy;
    }

    @Override
    public Serializable pkVal() {
        return this.rightsId;
    }

    @Override
    public void setId(String id) {
        setRightsId(id);
    }

    @Override
    public String getId() {
        return getRightsId();
    }
}
