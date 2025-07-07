package com.finscope.fraudscope.common.aop.log;

import static com.finscope.fraudscope.common.util.SafeUtil.safe;

import java.time.Instant;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import com.finscope.fraudscope.common.util.IpUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExceptionLogBuilder {

	private final HttpServletRequest request;

	public ExceptionLogContext build(JoinPoint joinPoint, Throwable ex) {
	
		return ExceptionLogContext.builder()
				.className(safe(() -> joinPoint.getSignature().getDeclaringTypeName(), "UnknownClass"))
				.methodName(safe(() -> joinPoint.getSignature().toShortString(), "UnknownMethod"))
				.path(safe(() -> request.getRequestURI(), "/unknown"))
				.httpMethod(safe(() -> request.getMethod(), "UNKNOWN"))
				.ipAddress(safe(() -> IpUtils.getClientIp(request), "unknown"))
				.userAgent(safe(() -> IpUtils.getUserAgent(request), "unknown"))
				.threadName(Thread.currentThread().getName())
				.exceptionType(safe(() -> ex.getClass().getSimpleName(), "UnknownException"))
				.exceptionMessage(safe(() -> ex.getMessage(), "No message")).timestamp(Instant.now().toString())
				.build();
	}

}
