package com.finscope.fraudscope.common.exception.enums;

import org.springframework.http.HttpStatus;

import com.finscope.fraudscope.common.enums.BaseEnum;

public enum ErrorType implements BaseEnum {

    USERNAME_OR_EMAIL_NOT_FOUND("1001", "error_type.username_or_email_not_found",HttpStatus.NOT_FOUND),
    USERNAME_OR_EMAIL_OR_PASSWORD_INVALID("1002", "error_type.username_or_email_or_password_invalid",HttpStatus.UNAUTHORIZED),
    USERNAME_ALREADY_EXISTS("1003", "error_type.username_already_exists",HttpStatus.CONFLICT),
    
    EMAIL_ALREADY_EXISTS("1004", "error_type.email_already_exists",HttpStatus.CONFLICT),
    
	ROLE_NOT_FOUND("1005", "error_type.role_not_found", HttpStatus.NOT_FOUND),
	
	INVALID_PASSWORD("1006", "error_type.invalid_password", HttpStatus.UNAUTHORIZED),
	
	ACCOUNT_NOT_VERIFIED("1007", "error_type.account_not_verified", HttpStatus.FORBIDDEN),
	
	VERIFICATION_TOKEN_NOT_FOUND("1008","error_type.verification_token_not_found", HttpStatus.NOT_FOUND),
	VERIFICATION_TOKEN_INVALID_OR_EXPIRED("1009","error_type.verification_token_invalid_or_expired", HttpStatus.CONFLICT),
	
	
    
    INVALID_TOKEN_SIGNATURE("1010", "Invalid token signature", HttpStatus.UNAUTHORIZED),
    MALFORMED_TOKEN("1011", "Malformed token", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("1012", "Token has expired", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN("1013", "Unsupported token", HttpStatus.BAD_REQUEST),
    GENERAL_TOKEN_EXCEPTION("1014", "Unexpected error while processing token", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_SENDING_FAILED("1015", "error_type.email_sending_failed", HttpStatus.INTERNAL_SERVER_ERROR),

    OTP_TOKEN_NOT_FOUND("1016","error_type.otp_token_not_found", HttpStatus.NOT_FOUND),
    OTP_TOKEN_INVALID_OR_EXPIRED("1017","error_type.verification_token_invalid_or_expired", HttpStatus.CONFLICT),
    OTP_TOKEN_IS_BLOCKED("1017","error_type_.otp_token_is_blocked", HttpStatus.BAD_REQUEST),
    OTP_TOKEN_UPDATE_FAILED("1018", "Failed to update OTP  Token.", HttpStatus.INTERNAL_SERVER_ERROR)

    
    ;
	
	
    private final String code;
    private final String i18nKey;
    private final HttpStatus httpStatus;

    private ErrorType(String code, String i18nKey,HttpStatus httpStatus) {
        this.code = code;
        this.i18nKey = i18nKey;
        this.httpStatus =httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getI18nKey() {
        return i18nKey;
    }
    
    public HttpStatus getHttpStatus() {
    	return httpStatus;
    }
}
