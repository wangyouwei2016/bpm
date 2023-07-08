package com.dstz.base.common.property;

/**
 * @author jinxia.hou
 * @Name PropertyEnum
 * @description: 属性管理枚举
 * @date 2022/3/915:12
 */
public enum PropertyEnum implements IBaseProperty {

    ADMIN_ACCOUNTS("admin.accounts", "admin", "admin账户"),
    JDBC_TYPE("spring.jdbc.dbType", "", "数据库类型"),
    FORM_DEF_BACKUP_PATH("formDefBackupPath", "", "表单备份地址"),
    FORM_TEMPLATE_URL("formTemplateUrl", "src/main/resources/templates", "表单模板URL地址"),

    // 流程相关

    TASK_REMOVE_MESSAGE_PUSH("task_remove_message_push", false, "任务删除消息推送"),
    DO_TASK_SUCCESSIONAL("doTaskSuccessional", false, "任务后面节点还是自己任务时，连续处理"),
    BPM_NOT_DEFAULT_BUTTONS("bpmNotDefaultButtons", "oppose,reject2Start,custMultiExecution,addSign,manualEnd", "流程非默认按钮"),

    // 上传相关
    UPLOADER_DEFAULT("uploader.default", "", "默认的文件上传器"),
    MINIO_BUCKET_NAME("ab.minio.bucketName", "", "minio bucketName"),
    UPLOADER_ORDINARY_PATH("uploader.ordinary.path", "", ""),
    BATCH_INSERT_EXCL_DATA_NUM("batch_insert_excl_data_num", 80, "导入excl数据时单次批量插入行数"),
    EXPORT_EXCL_MAX_NUM("export_excl_max_num", 50000, "导入excl最大行数"),
    // 用户登录
    LOGIN_COUNT("loginCount", 5, "连续登录验证失败最大次数"),
    LOGIN_FILED_LOCK_TIME_DESC("loginFailedlockTimeDesc", "24小时", "登录失败锁定账户的时间描述,请自行和缓存时间配置匹配，缓存配置24h，这里建议配置24小时，默认为24小时"),
    PWD_LOSE_COUNT("pwdLose", 180, "修改密码下次到期时间"),
    PWD_CHECK_RULE_KEY("checkingRuleKey", "^[A-Za-z0-9_!@#$%&*]{6,20}$", "密码规则"),
    PWD_CHECK_RULE_TXT("checkingRuleTxt", "密码长度在6-20位之间由数字、字母组合", "密码规则提示"),
    IS_OPEN_RESET_PWD_BY_EMAIL("isOpenResetPwdByEmail", false, "是否开启邮件找回密码功能"),
    LOGIN_CAPTCHA_KEY("captchaSwitch", false, "用户登陆是否校验验证码"),
    LOGIN_RESET_PWD("isResetPwd", false, "初始化密码是否必须重置后登录"),
    CHANGE_PWD_iS_LOG_OUT("changePwdIsLogOut", false, "修改密码是否退出登录"),
    CHANGE_PWD_iS_Exit_SYSTEM("changePwdIsExitSystem", false, "修改密码是否强制退出系统"),

    // 企业微信
    WX_QY_APP_SECRET("wxqy_appsecret", "", ""),

    //邮箱配置
    EMAIL_HOST("email_host", "", "email_host"),
    EMAIL_PORT("email_port", "", "email_port"),
    EMAIL_SSL("email_ssl", "", "email_ssl"),
    EMAIL_NICKNAME("email_nickname", "", "email_nickname"),
    EMAIL_ADDRESS("email_address", "", "email_address"),
    EMAIL_PASSWORD("email_password", "", "email_password"),


    IS_INTERFACE_AUTH("is_interface_auth", false, "是否开启接口级别鉴权"),

    /**
     * 事务消息重试次数
     */
    TRXM_RETRY_TIMES("ab.trxm.retry-times", 3, "事务消息重试次数"),

    /**
     * 百度SDK APPID
     */
    BAIDU_SDK_APPID("baidu.sdk.app-id", "", "百度SDK APPID"),

    /**
     * 百度SDK 密钥
     */
    BAIDU_SDK_SECRET("baidu.sdk.secret", "", "百度SDK 密钥"),


    ONLINE_DOC_SERVICE_URL("online_doc_service_url","http://192.168.1.141:18080","在线文档服务地址"),
    AGILEBPM_SERVICE_URL("agilebpm_service_url","http://192.168.1.6:8080","agilebpm服务地址"),
    ;


    private final String key;

    private final Object defaultValue;

    private final String desc;

    PropertyEnum(String key, Object defaultValue, String desc) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }
}
