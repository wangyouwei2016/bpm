package com.dstz.auth.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dstz.base.entity.AbModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资源角色关联表
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
@TableName("sys_resource_role")
public class ResourceRole extends AbModel<ResourceRole> {

    /**
     * ID
     */
    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 应用ID
     */
    @TableField("app_id_")
    private String appId;

    /**
     * 资源权限ID
     */
    @TableField("resource_id_")
    private String resourceId;

    /**
     * 角色ID
     */
    @TableField("role_id_")
    private String roleId;

    /**
     * 半选中
     */
    @TableField("half_checked_")
    private Integer halfChecked;

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
     * 角色别名。
     */
    @TableField(exist = false)
    protected String roleAlias;
    /**
     * 资源url连接。
     */
    @TableField(exist = false)
    protected String url;

    /**
     * 资源别名。
     */
    @TableField(exist = false)
    protected String resAlias;

    public String getRoleAlias() {
        return roleAlias;
    }

    public void setRoleAlias(String roleAlias) {
        this.roleAlias = roleAlias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResAlias() {
        return resAlias;
    }

    public void setResAlias(String resAlias) {
        this.resAlias = resAlias;
    }

    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAppId() {
        return appId;
    }
    
    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    public String getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    
    public String getRoleId() {
        return roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getHalfChecked() {
        return halfChecked;
    }

    public void setHalfChecked(Integer halfChecked) {
        this.halfChecked = halfChecked;
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

    @Override
    public Serializable pkVal() {
        return this.id;
    }


    public static final class Builder {
        private String id;
        private String appId;
        private String resourceId;
        private String roleId;
        private Integer halfChecked;
        private Date createTime;
        private String createBy;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder withResourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder withRoleId(String roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder withHalfChecked(Integer halfChecked) {
            this.halfChecked = halfChecked;
            return this;
        }

        public Builder withCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder withCreateBy(String createBy) {
            this.createBy = createBy;
            return this;
        }

        public ResourceRole build() {
            ResourceRole resourceRole = new ResourceRole();
            resourceRole.setId(id);
            resourceRole.setAppId(appId);
            resourceRole.setResourceId(resourceId);
            resourceRole.setRoleId(roleId);
            resourceRole.setHalfChecked(halfChecked);
            resourceRole.setCreateTime(createTime);
            resourceRole.setCreateBy(createBy);
            return resourceRole;
        }
    }
}
