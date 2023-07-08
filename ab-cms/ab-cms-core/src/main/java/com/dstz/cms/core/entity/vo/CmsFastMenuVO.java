package com.dstz.cms.core.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 快捷菜单表
 * </p>
 *
 * @author niu
 * @since 2022-03-11
 */
@TableName("cms_fast_menu")
public class CmsFastMenuVO implements Serializable{

    /**
     * 主键
     */
    private String id;

    /**
     * 归属人
     */
    private String userId;

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 图标code
     */
    private String icon;

    /**
     * 资源地址
     */
    private String resourceUrl;

    public CmsFastMenuVO(String id, String userId, String resourceId, String resourceName, String resourceUrl,String icon) {
        this.id = id;
        this.userId = userId;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceUrl = resourceUrl;
        this.icon = icon;
    }

    public CmsFastMenuVO( String resourceId, String resourceName, String resourceUrl,String icon) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceUrl = resourceUrl;
        this.icon = icon;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
