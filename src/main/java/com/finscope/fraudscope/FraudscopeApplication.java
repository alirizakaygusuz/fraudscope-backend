package com.finscope.fraudscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.ai.vectorstore.azure.autoconfigure.AzureVectorStoreAutoConfiguration.class })
public class FraudscopeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudscopeApplication.class, args);
	}

}
