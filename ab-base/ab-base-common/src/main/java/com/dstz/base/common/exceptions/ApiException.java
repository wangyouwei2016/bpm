package com.dstz.base.common.exceptions;

import com.dstz.base.common.codes.IBaseCode;

/**
 * <pre>
 * 接口通用异常定义，用于异常时包装异常信息
 * 作者:wacxhs
 * 邮箱:wacxhs@agilebpm.cn
 * 日期:2022-03-22
 * 版权: 深圳市大世同舟信息科技有限公司
 * </pre>
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -254913157871507053L;

    /**
     * 基础响应码
     */
    private final IBaseCode baseCode;

    public ApiException(IBaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }

    public ApiException(IBaseCode baseCode, Throwable throwable) {
        super(baseCode.getMessage(), throwable);
        this.baseCode = baseCode;
    }

    protected ApiException(IBaseCode baseCode, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(baseCode.getMessage(), cause, enableSuppression, writableStackTrace);
        this.baseCode = baseCode;
    }

    public IBaseCode getBaseCode() {
        return baseCode;
    }
}
