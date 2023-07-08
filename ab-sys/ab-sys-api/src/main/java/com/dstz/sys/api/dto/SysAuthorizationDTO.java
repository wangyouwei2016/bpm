package com.dstz.sys.api.dto;


import java.io.Serializable;
import java.util.Date;

/**
 * 通用资源授权配置
 */
public class SysAuthorizationDTO implements Serializable{

    /**
     * ID
     */
    private String rightsId;

    /**
     * 授权对象表分区用
     */
    private String rightsObject;

    /**
     * 授权目标ID
     */
    private String rightsTarget;

    /**
     * 权限类型
     */
    private String rightsType;

    /**
     * 授权标识
     */
    private String rightsIdentity;

    /**
     * 标识名字
     */
    private String rightsIdentityName;

    /**
     * 授权code=identity+type
     */
    private String rightsPermissionCode;

    /**
     * 创建时间
     */
    private Date rightsCreateTime;

    /**
     * 创建人
     */
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

    public SysAuthorizationDTO() {
    }

    public SysAuthorizationDTO(String rightsType, String rightsIdentity, String rightsIdentityName) {
        this.rightsType = rightsType;
        this.rightsIdentity = rightsIdentity;
        this.rightsIdentityName = rightsIdentityName;
    }

    public SysAuthorizationDTO(String rightsId, String rightsObject, String rightsTarget, String rightsType, String rightsIdentity, String rightsIdentityName, String rightsPermissionCode, Date rightsCreateTime) {
        this.rightsId = rightsId;
        this.rightsObject = rightsObject;
        this.rightsTarget = rightsTarget;
        this.rightsType = rightsType;
        this.rightsIdentity = rightsIdentity;
        this.rightsIdentityName = rightsIdentityName;
        this.rightsPermissionCode = rightsPermissionCode;
        this.rightsCreateTime = rightsCreateTime;
    }
}
