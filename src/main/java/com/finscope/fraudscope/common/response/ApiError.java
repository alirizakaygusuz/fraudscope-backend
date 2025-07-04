package com.finscope.fraudscope.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError<T> {
	private Integer status;

	private ErrorContext<T> errorContext;

}
