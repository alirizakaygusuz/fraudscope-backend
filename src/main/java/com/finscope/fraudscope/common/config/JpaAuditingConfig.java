package com.finscope.fraudscope.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {
	@Bean(name = "auditorAware")
	AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}
}
