package com.finscope.fraudscope.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.finscope.fraudscope.common.aop.log.ExceptionLogBuilder;
import com.finscope.fraudscope.common.aop.log.ExceptionLogContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionLoggingAspect {

	private final ExceptionLogBuilder exceptionLogBuilder;

	@AfterThrowing(pointcut = "execution(* com.finscope.fraudscope..service..*(..))", throwing = "ex")
	public void logException(JoinPoint joinPoint, Throwable ex) {

		ExceptionLogContext context = exceptionLogBuilder.build(joinPoint, ex);
		
		log.error("Exception occured in service layer:\n{}",context);
	}

}
