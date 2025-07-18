package com.finscope.fraudscope.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;

public class SecurityContextUtil {

	public static String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BaseException(new ErrorMessage(ErrorType.AUTHENTICATION_NOT_VALID));
        }

        String username = authentication.getName();
        if (username == null || username.isBlank()) {
            throw new BaseException(new ErrorMessage(ErrorType.AUTHENTICATION_USERNAME_NOT_VALID));
        }

        return username;
	}
}
