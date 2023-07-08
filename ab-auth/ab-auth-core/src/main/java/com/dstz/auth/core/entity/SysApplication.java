package com.dstz.auth.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dstz.auth.authentication.api.model.ISysApplication;
import com.dstz.base.entity.AbModel;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 应用
 * </p>
 *
 * @author lightning
 * @since 2022-08-03
 */
@TableName("sys_application")
public class SysApplication extends AbModel<SysApplication>  implements ISysApplication {

    /**
     * ID
     */
    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 应用名
     */
    @TableField("name_")
    private String name;

    /**
     * 编码
     */
    @TableField("code_")
    private String code;

    /**
     * 密匙
     */
    @TableField("secret_")
    private String secret;

    /**
     * 资源集合
     */
    @TableField("resource_ids_")
    private String resourceIds;

    /**
     * 授权范围
     */
    @TableField("scope_")
    private String scope;

    /**
     * 刷新秒数
     */
    @TableField("refresh_token_validity_")
    private Integer refreshTokenValidity;

    /**
     * 有效期
     */
    @TableField("access_token_validity_")
    private Integer accessTokenValidity;

    /**
     * 授权类型
     */
    @TableField("grant_types_")
    private String grantTypes;

    /**
     * 自动授权
     */
    @TableField("autoapprove_")
    private Integer autoapprove;

    /**
     * 权限
     */
    @TableField("authorities_")
    private String authorities;

    /**
     * 系统地址，空则为当前系统
     */
    @TableField("url_")
    private String url;

    /**
     * 回调地址
     */
    @TableField("redirect_uri_")
    private String redirectUri;

    /**
     * 打开方式
     */
    @TableField("open_type_")
    private String openType;

    /**
     * 是否可用
     */
    @TableField("enabled_")
    private Integer enabled;

    /**
     * 是否默认打开
     */
    @TableField("is_default_")
    private Integer isDefault;

    /**
     * 描述备注
     */
    @TableField("desc_")
    private String desc;

    /**
     * 扩展配置
     */
    @TableField("config_")
    private String config;

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
     * 更新人ID
     */
    @TableField(value = "update_by_", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新人
     */
    @TableField(value = "updater_", fill = FieldFill.INSERT_UPDATE)
    private String updater;


    /**
     * 应用类型 0 web应用 1 移动端 2第三方应用
     */
    @TableField("app_type_")
    private Integer appType;

    @Override
    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

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
    
    public String getSecret() {
        return secret;
    }
    
    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    public String getResourceIds() {
        return resourceIds;
    }
    
    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
    
    public String getScope() {
        return scope;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
    }
    
    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }
    
    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }
    
    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }
    
    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }
    
    public String getGrantTypes() {
        return grantTypes;
    }
    
    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }
    
    public Integer getAutoapprove() {
        return autoapprove;
    }
    
    public void setAutoapprove(Integer autoapprove) {
        this.autoapprove = autoapprove;
    }
    
    public String getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getRedirectUri() {
        return redirectUri;
    }
    
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
    
    public String getOpenType() {
        return openType;
    }
    
    public void setOpenType(String openType) {
        this.openType = openType;
    }
    
    public Integer getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
    
    public Integer getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getConfig() {
        return config;
    }



    public void setConfig(String config) {
        this.config = config;
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
    public String getUpdateBy() {
        return updateBy;
    }
    
    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
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
    public Serializable pkVal() {
        return this.id;
    }
}
