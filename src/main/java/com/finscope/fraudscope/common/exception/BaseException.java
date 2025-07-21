package com.finscope.fraudscope.common.exception;

public class BaseException extends RuntimeException {

	private final ErrorMessage errorMessage;

	public BaseException(ErrorMessage errorMessage) {
		super(errorMessage.buildErrorMessage());
		this.errorMessage = errorMessage;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

}
