package com.dstz.base.common.constats;

/**
 * AB 模块REST常量
 *
 * @author wacxhs
 * @since 2022-01-22
 */
public class AbAppRestConstant {

    private AbAppRestConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }


    /**
     * ORG 服务前缀（目前的基础微服务）
     */
    public static final String ORG_SERVICE_PREFIX = "${ab.org-rest-prefix:/ab-org}";

    /**
     * BPM 服务前缀（目前的基础微服务）
     */
    public static final String BPM_SERVICE_PREFIX = "${ab.bpm-rest-prefix:/ab-bpm}";
    
    /**
     * DEMO 服务前缀（目前依附在bpm微服务下）
     */
    public static final String DEMO_SERVICE_PREFIX = BPM_SERVICE_PREFIX + "/demo";

    /**
     * AUTH 服务前缀（目前依附在org微服务下）
     */
    public static final String AUTH_SERVICE_PREFIX = ORG_SERVICE_PREFIX + "/auth";

    /**
     * CMS CMS前缀（目前依附在bpm微服务下）
     */
    public static final String CMS_SERVICE_PREFIX = BPM_SERVICE_PREFIX + "/cms";
    
    /**
     * BIZ前缀（目前依附在bpm微服务下）
     */
    public static final String BIZ_SERVICE_PREFIX = BPM_SERVICE_PREFIX + "/biz";
    
    /**
     * SYS前缀（目前依附在bpm微服务下）
     */
    public static final String SYS_SERVICE_PREFIX = BPM_SERVICE_PREFIX + "/sys";
}
