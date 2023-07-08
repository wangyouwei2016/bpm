package com.dstz.base.common.enums;

import com.dstz.base.common.codes.IBaseCode;


/**
 * 全局接口响应码
 *
 * @author wacxhs
 * @since 2022-01-22
 */
public enum GlobalApiCodes implements IBaseCode {

    /**
     * 操作成功
     */
    SUCCESS("Success", "操作成功"),

    /**
     * Illegal parameters.
     */
    PARAMETER_INVALID("ParameterInvalid", "Illegal parameters {}"),
    
	/**
     * Parameter unallowed.
     */
    PARAMETER_UNALLOWED("ParameterUnallowed", "Parameter not allowed {}"),

    /**
     * User not authorized to operate on the specified resource.
     */
    ACCESS_FORBIDDEN("AccessForbidden", "User not authorized to operate on the specified resource."),

    /**
     * The specified resource is not found.
     */
    RESOURCE_NOT_FOUND("ResourceNotFound", "The specified resource is not found."),
    
    /**
     * 解析失败
     */
    PARSE_ERROR ("parseError", "{}解析失败！"),
    
    /**
     * {}数据已经存在{}
     */
    DATA_DUPLICATION ("DataDuplication", "{}数据已经存在{}"),
    
    DATA_NOT_FOUND ("DataDuplication", "{}数据不存在{}"),

    
    NO_LOGIN_USER ("noLoginUser", "登录用户不存在！"),
    
    /**
     * 删除失败，存在级联数据
     */
    DELETE_FAILED_HAS_ASSOCIATED_DATA ("deleteFailedHasAssociatedData", "删除失败，存在相关联的数据：{}"),
    
    /**
     * 通用异常
     */
    BASE_COMMON_ERROR ("baseCommonError", "base模块通用异常"),

    /**
     * The request processing has failed due to some unknown error.
     */
    INTERNAL_ERROR("InternalError", "系统内部出错"),

    /**
     * 远程调用错误
     */
    REMOTE_CALL_ERROR("RemoteCallError", "{}"),

    /**
     * 登录会话超时
     */
    LOGIN_INVALID("LoginValid", "登录会话超时，请重新登录!"),

    /**
     * 请求限流
     */
    REQUEST_FLOW_LIMITING("RequestFlowLimiting", "服务器忙，请稍后再试!"),

    /**
     * 服务降级
     */
    SERVICE_DEGRADE("ServiceDegrade", "服务不可用，请稍后再试!"),

    /**
     * 数据已被他人更新（乐观锁）
     */
    DATA_VERSION_OLD("DataVersionOld", "数据已被更新，请刷新页面重新编辑保存");
	

    private final String code;

    private final String message;

    GlobalApiCodes(String code, String message) {
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
