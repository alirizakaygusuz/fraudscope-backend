package com.finscope.fraudscope.common.exception;

import com.finscope.fraudscope.common.exception.enums.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage {

	private final ErrorType errorType;

	private final String additionalInfo;

	
	public ErrorMessage(ErrorType errorType) {
		this(errorType, null);
	}

	public String buildErrorMessage() {
		StringBuilder builder = new StringBuilder();

		builder.append(errorType.getCode())
				.append(" ")
				.append(errorType.getI18nKey());

		if (additionalInfo != null && !additionalInfo.isBlank()) {
			builder.append(" : ").append(additionalInfo);
		}

		return builder.toString();
	}

}
