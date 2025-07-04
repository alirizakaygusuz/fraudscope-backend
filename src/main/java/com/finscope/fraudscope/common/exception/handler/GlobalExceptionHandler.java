package com.finscope.fraudscope.common.exception.handler;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.response.ApiError;
import com.finscope.fraudscope.common.response.ErrorContext;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ApiError<?>> handleUnexpectedException(Exception exception, WebRequest webRequest) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (exception instanceof IllegalArgumentException) {
			status = HttpStatus.BAD_REQUEST;
		} else if (exception instanceof IllegalStateException) {
			status = HttpStatus.CONFLICT;
		}

		return ResponseEntity.status(status)
				.body(createApiError("Unexpected error: " + exception.getMessage(), webRequest, status));
	}

	@ExceptionHandler(value = { BaseException.class })
	public ResponseEntity<ApiError<?>> handleBaseException(BaseException exception, WebRequest webRequest) {
		return ResponseEntity.status(exception.getErrorMessage().getErrorType().getHttpStatus()).body(createApiError(
				exception.getErrorMessage(), webRequest, exception.getErrorMessage().getErrorType().getHttpStatus()));
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<ApiError<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest webRequest) {
		Map<String, List<String>> validationErrors = new HashMap<>();

		for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
			String field = ((FieldError) objectError).getField();
			validationErrors.computeIfAbsent(field, k -> new ArrayList<>()).add(objectError.getDefaultMessage());
		}

		return ResponseEntity.badRequest().body(createApiError(validationErrors, webRequest, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError<?>> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {

		Map<String, List<String>> validationErrors = new HashMap<>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String field = violation.getPropertyPath().toString();
			validationErrors.computeIfAbsent(field, k -> new ArrayList<>()).add(violation.getMessage());
		}

		return ResponseEntity.badRequest().body(createApiError(validationErrors, request, HttpStatus.BAD_REQUEST));
	}

	private String getHostName() {
		try {
			return Inet4Address.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "unknown";
		}
	}

	private <T> ApiError<T> createApiError(T message, WebRequest request, HttpStatus httpStatus) {
		ErrorContext<T> details = new ErrorContext<>(request.getDescription(false).substring(4), new Date(),
				getHostName(), message);

		return new ApiError<>(httpStatus.value(), details);
	}
}
