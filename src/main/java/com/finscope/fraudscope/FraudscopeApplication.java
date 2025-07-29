package com.finscope.fraudscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = "com.finscope.fraudscope")
@EntityScan(basePackages = "com.finscope.fraudscope")
@EnableJpaRepositories(basePackages = "com.finscope.fraudscope")
@SpringBootApplication(exclude = {
		org.springframework.ai.vectorstore.azure.autoconfigure.AzureVectorStoreAutoConfiguration.class })
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class FraudscopeApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FraudscopeApplication.class);
		app.run(args);
	}

}
