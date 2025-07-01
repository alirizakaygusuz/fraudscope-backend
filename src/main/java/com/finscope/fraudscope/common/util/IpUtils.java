package com.finscope.fraudscope.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtils {

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        return ip != null ? ip : request.getRemoteAddr();
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
}
