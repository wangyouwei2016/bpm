package com.dstz.auth.authentication.api.constant;

/**
 * auth模块常量
 *
 * @author lightning
 */
public class AuthApiConstant {
    private AuthApiConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * 来源-system
     */
    public static final String SYSTEM = "system";
    /**
     * 系统别名
     */
    public static final String AGILEBPM = "agilebpm";
    /**
     * 登录超时跳转url
     */
    public static final String SSO_REDIRECTURL = "SSO_redirectUrl";

    /**
     * 超管账户
     */
    public static final String ADMIN = "admin";

    //----------oauth2相关配置------------

    /**
     * 令牌默认有效期2小时
     */
    public static final Integer ACCESSTOKEN_VALIDITY_SECONDS = 7200;

    /**
     * 刷新令牌默认有效期3天
     */
    public static final Integer REFRESHTOKEN_VALIDITY_SECONDS = 259200;


    public static final String AUTHORIZATION = "Authorization";

    public static final String BEARER = "Bearer";

    /**
     * 配置令牌端点安全约束配置
     */
    public static final String TOKEN_SERVER_SECURITY_CONFIGURER = "permitAll()";

    /**
     * 定制授权页
     */
    public static final String TOKEN_SERVER_AUTH_DEFAULTPATH = "/oauth/confirm_access";

    /**
     * 定制授权页
     */
    public static final String TOKEN_SERVER_AUTH_CUSTOMPATH = "/customer/confirm_access";

    /**
     * oauth 客户端key
     */
    public static final String OAUTH_TOKEN_CLIENT_KEY = "client_id";

    public static final String OAUTH_TOKEN_CLIENT_SECRET = "client_secret";


    /**
     * oauth 客户端 refresh_token
     */
    public static final String OAUTH_TOKEN_REFRESH_TOKEN = "refresh_token";

    /**
     * oauth 客户端grant_type
     */
    public static final String OAUTH_TOKEN_GRANT_TYPE = "grant_type";






    /**
     * oauth 客户端username
     */
    public static final String OAUTH_TOKEN_USER_NAME = "username";

    /**
     * oauth 客户端password
     */
    public static final String OAUTH_TOKEN_PASSWORD = "password";

}
