package com.finscope.fraudscope.authentication.verification.enums;

public enum TokenPurpose {
    ACCOUNT_VERIFICATION(
        "Account Verification",
        "Please verify your account by clicking the link below.",
        "/api/auth/verify"
    ),
    TWO_FACTOR_AUTH(
    	    "Two-Factor Authentication",
    	    "Please enter the OTP sent to your email to complete login.",
    	    "/api/auth/verify-otp"
    ),
    PASSWORD_RESET(
        "Password Reset",
        "You requested a password reset. Click the link below to reset your password.",
        "/api/auth/reset-password"
    ),
    EMAIL_CHANGE(
        "Email Change",
        "Please confirm your new email address by clicking the link below.",
        "/api/auth/change-email"
    );
	

    private final String title;
    private final String description;
    private final String path;

    TokenPurpose(String title, String description, String path) {
        this.title = title;
        this.description = description;
        this.path = path;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPath() { return path; }
}
