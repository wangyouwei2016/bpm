package com.dstz.base.common.constats;

/**
 * 缓存区域常量
 *
 * @author wacxhs
 */
public class AbCacheRegionConstant {

    private AbCacheRegionConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }


    /**
     *登录相关token
     */
    public static final String LOGIN_CACHE_REGION = "LOGIN_TOKEN";

    /**
     *用户找回密码相关
     */
    public static final String LOGIN_PWD_REGION = "PWD_TOKEN";

    /**
     * 系统资源相关
     */
    public static final String SYS_RESOURCE = "SYS_RESOURCE";

    /**
     * 工作交接相关
     */
    public static final String WORK_HANDOVER_REGION = "WORK_HANDOVER";

    /**
     * 系统属性想关
     */
    public static final String PROPERTIES_CACHE_REGION = "SYSPROPETIES";

    /**
     * 字典相关
     */
    public static final String DICT_CACHE_REGION = "DICT";

    /**
     * 审计日志元信息
     */
    public static final String AUDIT_LOG_META = "AUDIT_LOG_META";


    /**
     * 消息相关
     */
    public static final String MSG_REGION = "MSG_REGION";

    /**
     * 第三方登录token
     */
    public static final String THIRD_TOKEN = "THIRD_TOKEN";
    /**
     * agileBPM 流程定义
     */
    public static final String BPM_PROCESS_DEF = "BPM_PROCESS_DEF";

    /**
     * activiti ProcessDefinitionEntity
     */
    public static final String BPM_ACT_PROCESS_DEF = "BPM_ACT_PROCESS_DEF";

    /**
     * 数据权限
     */
    public static final String DATA_PRIVILEGE = "DATA_PRIVILEGE";
    
    /**
     * 业务对象的缓存
     */
    public static final String BIZ_OBJECT_REGION = "BIZ_OBJECT";

    /**
     * 系统应用
     */
    public static final String SYS_APPLICATION = "SYS_APPLICATION";

    /**
     * 系统会话属性
     */
    public static final String SYS_SESSION_ATTRIBUTE = "SYS_SESSION_ATTRIBUTE";

    /**
     *
     */
    public static final String OAUTH_REGION = "";
}
