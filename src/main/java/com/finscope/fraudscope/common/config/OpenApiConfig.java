package com.finscope.fraudscope.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI fraudScopeOpenAPI() {
		String securitySchemeName = "bearerAuth";

		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components().addSecuritySchemes(securitySchemeName,
						new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer")
								.bearerFormat("JWT")
								.description("Use the format 'Bearer {token}' in the Authorization header.\n"
										+ "You can obtain a token from `/api/auth/login` after email verification.")))
				.info(new Info().title("FraudScope Backend API")
						.description("Fraud detection & user management backend system for fintech applications.")
						.version("v1.0.0")
						.contact(new Contact().name("Ali Rıza Kaygusuz").email("ali.rizakaygusuz@hotmail.com"))
						.license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")))
				.externalDocs(new ExternalDocumentation().description("Project GitHub Repo")
						.url("https://github.com/alirizakaygusuz/fraudscope-backend"))
				.addServersItem(new Server().url("http://localhost:8080").description("Local Dev Environment"));
	}
}
