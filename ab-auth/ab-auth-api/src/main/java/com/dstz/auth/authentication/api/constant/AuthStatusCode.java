package com.dstz.auth.authentication.api.constant;

import com.dstz.base.common.codes.IBaseCode;

/**
 * 鉴权接口响应码
 *
 * @author lightning
 */
public enum AuthStatusCode implements IBaseCode {

    /**
     * 必传参数为空
     */
    PARAM_CLIENT_ID_IS_NOT_FOUND("param_client_id_is_not_found", "必传参数clientId为空"),
    /**
     * 账户或密码不对
     */
    LOGIN_ERROR("login_error", "账户或密码不对"),

    USER_NAME_OR_PASSORD_ERROR("user_name_or_passord_error", "{}"),
    /**
     * 应用配置错误
     */
    APP_CONFIG_ERROR("app_config_error", "应用配置错误"),
    /**
     * 账户找不到
     */
    ACCOUNT_NOT_FIND("account_not_find", "账户不存在"),
    /**
     * 账户不能为空
     */
    ACCOUNT_CANNOT_BE_EMPTY("account_cannot_be_empty", "账户不能为空"),
    /**
     * 密码不能为空
     */
    PASSWORD_CANNOT_BE_EMPTY("password_cannot_be_empty", "密码不能为空"),
    /**
     * 帐号已禁用
     */
    ACCOUNT_DISABLED("account_disabled", "帐号已禁用"),
    /**
     * 帐号已锁定
     */
    ACCOUNT_IS_LOCKED("account_is_locked", "帐号已锁定"),
    /**
     * 帐号已过期
     */
    ACCOUNT_HAS_EXPIRED("account_has_expired", "帐号已过期"),
    /**
     * 账号或密码错误次数过多，将禁止登录
     */
    DISABLE_LOGIN("disable_login", "账号或密码错误次数过多，将禁止登录{}"),

    DISABLE_LOGIN_WARN("disable_login_warn","账户密码错误，已经失败 {} 次，若连续失败 {} 次将禁止登录{}"),
    /**
     * 验证码不能为空
     */
    CAPTCHA_CANNOT_BE_EMPTY("captcha_cannot_be_empty", "验证码不能为空"),
    /**
     * 验证类型
     */
    GRANTTYPE_CANNOT_BE_EMPTY("granttype_cannot_be_empty", "验证类型不能为空"),
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR("captcha_error", "验证码错误,请重试"),
    /**
     * 密码为初始密码
     */
    PASSWORD_NEEDS_TO_BE_CHANGED("password_needs_to_be_changed", "密码为初始密码,请修改密码后登录"),
    /**
     * 当前用户尚未分配任何资源
     */
    USER_HAS_NOT_ASSIGNED_ANY_RESOURCES("user_has_not_assigned_any_resources", "当前用户尚未分配任何资源"),

    APPLICATION_NO_PERMISSIONS("application_no_permissions", "没有该应用的权限：{}"),

    RESOURCES_CODE_REPEAT("resources_code_repeat", "别名已存在，请修改！：{}"),

    DELETE_RESOURCES_ERROR("delete_resources_error", "删除子系统资源失败"),

    SYS_IS_NOT_DEFINITION("sys_is_not_definition", "当前系统“{} ”不存在！ 请添加子系统，并配置资源菜单！\" : \"您没有当前系统“{}”的访问权限！请联系管理员。"),

    PARAM_IS_NULL("param_is_null", "必传参数 {} 为空"),

    APPLICATION_GET_TOKEN_ERROR("application_get_token_error", "用户：{} 获取token异常 clientid：{}"),
    APPLICATION_REFRESH_TOKEN_ERROR("application_refresh_token_error", "刷新token异常 clientid：{}"),
    /**
     * 根据当前clientid未查到数据
     */
    LOADCLIENT_BY_CLIENTID_ERROR("loadclient_by_clientid_error", "根据当前clientid未查到数据"),

    /**
     * There is no client authentication. Try adding an appropriate authentication filter
     */
    NO_CLIENT_AUTHENTICATION("no_client_authentication", "没有找到匹配的身份验证过滤器"),

    LOGIN_TIMEOUT("LOGIN_TIMEOUT", "登录超时,请重新登录"),

    NO_AUTH_TOKEN("no_auth_token", "请求未传入鉴权信息"),

    TOKEN_INVALID("token_invalid", "登录超时"),

    NOT_PERMISSION("not_permission", "权限不足"),

    LOAD_CLIENT_BY_CLIENTID_ERROR("load_client_by_clientid_error", "加载应用信息异常：{}"),

    NO_FIND_APP("no_find_app", "根据client_id：{} 找不到对应 应用"),

    RESOUCE_TYPE_CONSTANT_ERROR("resouce_type_constant_error", "菜单类型的资源，上级资源 {} 也必须是菜单！"),

    METHOD_NOT_ALLOWED("method_not_allowed", "方法未被允许"),

    SERVICE_ERROR("service_error", "内部错误"),

    LOGIN_AUTHORIZATION_FILTER_ERROR("login_authorization_filter_error", "鉴权过滤器AuthorizationTokenCheckFilter获取token异常"),

    /**
     * 非法的当前用户组织，一般引起该错误，例如管理员分配了组织或者用户修改了请求参数
     */
    ILLEGAL_CURRENT_ORG("IllegalCurrentOrg", "非法的当前用户组织, 非法组织编码：{}"),

    /**
     * 用户未分配组织
     */
    USER_UNABSORBED_ORG("UNABSORBED_ORG", "用户未分配组织"),

    /**
     * 应用无菜单资源
     */
    APP_NO_RESOURCE("AppNoResource", "切换系统失败，该系统下没有可访问的菜单资源"),


    /**
     * 该请求未分配给任何角色，或接口不存在
     */
    API_NONEXISTENT_OR_NO_ACCESS("api_nonexistent_or_no_access", "该请求未分配给任何角色，或接口不存在：{}"),


    /**
     * 您没有该接口的访问权限
     */
    API_NO_ACCESS("api_no_access", "您没有该接口的访问权限："),


    APPCLICATION_ENABLE_DEFAULT_MOBILE_APP_NOT_FIND("appclication_enable_default_mobile_app_not_find", "系统找不到移动端默认应用，请先配置移动端默认应用"),


    ;

    private final String code;
    private final String desc;

    AuthStatusCode(String code, String description) {
        this.code = code;
        this.desc = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return desc;
    }
}
