package com.dstz.auth.constant;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

/**
 * 权限类型
 *
 * @author lightning
 */
public class PlatformConsts {

    /**
     * 匿名级
     */
    private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * 验证权限
     */
    private final static String ROLE_AUTH = "ROLE_AUTH";

    public static final ConfigAttribute ROLE_CONFIG_ANONYMOUS = new SecurityConfig(ROLE_ANONYMOUS);

    public static final ConfigAttribute URL_AUTH = new SecurityConfig(ROLE_AUTH);
}
