package com.dstz.auth.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dstz.base.entity.AbModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统权限资源定义
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
@TableName("sys_resource")
public class SysResource extends AbModel<SysResource> {

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
     * 权限编码
     */
    @TableField("code_")
    private String code;

    /**
     * 名字
     */
    @TableField("name_")
    private String name;

    /**
     * 路由地址
     */
    @TableField("url_")
    private String url;

    /**
     * 是否启用
     */
    @TableField("enable_")
    private Integer enable;

    /**
     * 是否默认打开
     */
    @TableField("opened_")
    private Integer opened;

    /**
     * 是否API资源
     */
    @TableField("is_api_")
    private Integer isApi;

    /**
     * 图标
     */
    @TableField("icon_")
    private String icon;

    /**
     * menu，button，API
     */
    @TableField("type_")
    private String type;

    /**
     * 排序
     */
    @TableField("sn_")
    private Integer sn;

    /**
     * 父节点ID
     */
    @TableField("parent_id_")
    private String parentId;

    /**
     * 路径
     */
    @TableField("path_")
    private String path;


    /**
     * 创建时间
     */
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getOpened() {
        return opened;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    public Integer getIsApi() {
        return isApi;
    }

    public void setIsApi(Integer isApi) {
        this.isApi = isApi;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
