package com.dstz.auth.authentication.api.model;

/**
 * 系统应用
 *
 * @author lightning
 */
public interface ISysApplication {

    /**
     * 返回 主键
     *
     * @return
     */
    String getId();

    /**
     * 返回 系统名称
     *
     * @return
     */
    String getName();

    /**
     * 返回 系统别名
     *
     * @return
     */
    String getCode();


    /**
     * 密匙
     */
    String getSecret();

    /**
     * 刷新秒数
     */
    Integer getRefreshTokenValidity();

    /**
     * 有效期
     */
    Integer getAccessTokenValidity();

    /**
     * 系统地址，空则为当前系统
     */
    String getUrl();


    /**
     * 回调地址
     */
    String getRedirectUri();

    /**
     * 打开方式
     */
    String getOpenType();


    /**
     * 返回 是否可用 1 可用，0 ，不可用
     *
     * @return
     */
    Integer getEnabled();

    /**
     * 是否默认
     */
    Integer getIsDefault();

    /**
     * 描述备注
     */
    String getDesc();

    /**
     * 扩展配置
     */
    String getConfig();

    /**
     * 是否可用app
     * @return
     */
    Integer getAppType();


}