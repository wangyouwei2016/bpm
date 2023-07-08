package com.dstz.base.common.exceptions;

import com.dstz.base.common.codes.IBaseCode;

/**
 * 业务消息，反馈给请求端，后端不记录
 *
 * @author wacxhs
 */
public class BusinessMessage extends ApiException {

    private static final long serialVersionUID = -3468412951817107639L;

    public BusinessMessage(IBaseCode baseCode) {
        super(baseCode, null, false, false);
    }
}
