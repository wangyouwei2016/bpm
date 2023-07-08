package com.dstz.base.common.exceptions;

/**
 * @author wacxhs
 */
public class IllegalLicenseException extends RuntimeException {

	private static final long serialVersionUID = 3877439584893020602L;

	public IllegalLicenseException(String message) {
		super(message, null, false, false);
	}
}
