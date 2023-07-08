package com.dstz.base.common.exceptions;

import com.dstz.base.common.codes.IBaseCode;

/**
 * 业务逻辑异常，常常为可预料异常，此异常常常是开发时，非法操作信息提示。比如 流程表单丢失！
 *
 * @author wacxhs
 */
public class BusinessException extends ApiException {

    private static final long serialVersionUID = 4851159171511226800L;

    public BusinessException(IBaseCode baseCode) {
        super(baseCode);
    }

    public BusinessException(IBaseCode baseCode, Throwable throwable) {
        super(baseCode, throwable);
    }

	public static void te(IBaseCode baseCode, Throwable throwable) {
		throw new BusinessException(baseCode,throwable);
	}

	public static void te(IBaseCode baseCode) {
		throw new BusinessException(baseCode);
	}
}
