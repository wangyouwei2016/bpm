package com.dstz.org.core.constant;

import com.dstz.base.common.codes.IBaseCode;

public enum OrgStatusCode implements IBaseCode {

    FROM_MODIFICATION_DEMO_PWD("fromModificationDemoPassword", "为了防止恶意破坏演示数据，禁止修改密码！<br/>您的访问信息已经被我们统计！"),
    USER_DOES_NOT_EXIST("userDoesNotExist", "用户不存在!"),
    OLD_PWD_INPUT_ERROR("oldPwdInputError", "原密码输入错误!"),
    EMAIL_DOES_NOT_EXIST("emailDoesNotExistPleaseContactAdminResetPwd", "邮箱不存在，请联系管理员重置密码"),
    EMAIL_INPUT_ERROR("emailInputErrorPleaseEnterCorrectEmail", "邮箱输入错误，请输入正确的邮箱"),
    NEW_PWD_IS_DIFFERENT_CONFIRM_PWD("newPasswordIsDifferentFromConfirmPassword", "新密码和确认密码不相同"),
    VERIFICATION_CODE_IS_EXPIRED(" verificationCodeIsExpired", "验证码已过期，请重新获取"),
    PWD_RESET_FUNCTION_IS_NOT_ENABLED("PasswordResetFunctionIsNotEnabled ", "密码重置功能暂未启用，请联系管理员重置密码！"),
    ROLE_CODE_IS_EXIST("roleCodeIsExistInTheSystem", "角色编码在系统中已存在！"),
    GROUP_CODE_IS_EXIST("groupCodeIsExistInTheSystem", "组织编码在系统中已存在！"),
    ACCOUNT_IS_EXIST("accountIsExist", "账号已存在，请修改！"),
    INPUT_INFORMATION_IS_EMPTY("inputInformationIsEmpty", "输入错误，请重新设置用户信息"),
    DEL_FAILED_PARAM_IS_EMPTY("deleteFailedParamIsEmpty", "删除失败，参数不能为空"),
    OPERATION_FAILURE("operationFailure", "操作失败，参数异常"),
    IS_SUPER_ADMIN("isSuperAdmin","只有超管才能重置密码，请确认当前操作用户"),
    NOT_ALLOW_DELETE_ADMIN("notAllowDeleteAdmin","删除用户失败，不能删除系统管理员"),
    NOT_FIND_USER_BY_OPENID("not_find_user_by_openid", "用户尚未绑定第三方登录！"),

    /**
     * 关系检查
     */
    ORG_RELATION_REMOVE_CHECK("OrgRelationRemoveCheck", "{}");

    private final String code;
    private final String message;

    OrgStatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
