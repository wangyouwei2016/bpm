package com.dstz.base.common.exceptions;

/**
 * 乐观锁字段值异常
 *
 * @author wacxhs
 */
public class OptimisticLockingFieldValueException extends RuntimeException{

	private static final long serialVersionUID = 8448037384631122436L;

	public OptimisticLockingFieldValueException(String message) {
		super(message, null, false, false);
	}
}
