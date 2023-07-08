package com.dstz.auth.authentication.api.constant;

import javax.swing.*;

/**
 * 缓存key常量
 *
 * @author lightning
 */
public class AuthCacheKeyConstant {

    /**
     * auth_client 方法缓存key
     */
    public static final String CACHE_REGION_AUTH_CLIENT = "OAUTH_REGION";
    /**
     * auth_client 方法缓存key SpEL表达式
     */
    public static final String AUTH_CLIENT_RECEIVE_EL = "'authClientEl:'.concat(#root.args[0])";

    public static final String AUTH_APP_LIST_EL = "'authAppListEl:";

    /**
     * auth_client 方法缓存key SpEL表达式
     */
    public static final String CACHE_EVICT_AUTH_CLIENT_RECEIVE_EL = "'authClientEl:'.concat(#root.args[0].getCode())";

    /**
     * 根据url缓存权限映射
     */
    public static final String URL_ROLE_MAPPING = "agilebpm:sys:resoucesUrlRoleMapping:";
    /**
     * 登录用户缓存key
     */
    public static final String LOGIN_USER_CACHE_KEY = "agilebpm:loginUser:";

    /**
     * 用户token缓存
     */
    public static final String USER_TOKEN = "user:token:";


    /**
     * 用户登录次数统计
     */
    public static final String USER_LOGIN_COUNT = "user:login:count:";

    /**
     * 忽略鉴权列表
     */
    public static final String IGNORE_AUTH_URL_LIST = "'ignore:auth:url'";

    /**
     * 判断url是否需要方法鉴权
     */
    public static final String IS_ROLE = "'isRole:'.concat(#root.args[0])";


}
