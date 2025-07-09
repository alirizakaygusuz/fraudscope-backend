package com.finscope.fraudscope.common.aop.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionLogContext {

	private String className;

	private String methodName;

	private String path;

	private String httpMethod;

	private String ipAddress;

	private String userAgent;

	private String threadName;

	private String exceptionType;

	private String exceptionMessage;

	private String timestamp;
}
