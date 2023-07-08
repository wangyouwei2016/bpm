package com.dstz.auth.authentication.api.model;

import com.dstz.base.api.model.Tree;
/**
 * 系统资源
 *
 * @author lightning
 */
public interface ISysResource extends Tree {


    /**
     * 返回 主键
     *
     * @return
     */
    public String getId();

    /**
     * 返回 子系统ID
     *
     * @return
     */
    public String getAppId();

    /**
     * 返回 资源别名
     *
     * @return
     */
    public String getCode();


    public String getPath();

    /**
     * 返回 资源名
     *
     * @return
     */
    public String getName();

    /**
     * 返回 默认地址
     *
     * @return
     */
    public String getUrl();

    /**
     * 返回 显示到菜单(1,显示,0 ,不显示)
     *
     * @return
     */
    public Integer getEnable();

    /**
     * 返回 OPENED_
     *
     * @return
     */
    public Integer getOpened();

    /**
     * 是否API资源
     */

    Integer getIsApi();

    /**
     * 返回 图标
     *
     * @return
     */
    public String getIcon();

    /**
     * 返回类型
     *
     * @return
     */
    public String getType();

    /**
     * 返回 排序
     *
     * @return
     */
    public Integer getSn();


    public String getParentId();

    Boolean getHidden();


}
