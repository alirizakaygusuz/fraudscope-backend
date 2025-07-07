package com.finscope.fraudscope.common.util;

import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SafeUtil {
	private static final Logger log = LoggerFactory.getLogger(SafeUtil.class);

	private SafeUtil() {

	}

	public static <T> T safe(Supplier<T> supplier, T fallback) {
		try {
			return supplier.get();
		} catch (Exception e) {
			log.warn("Safe execution failed: {}",e.getMessage(),e);
			return fallback;
		}
	}
	
	
}
