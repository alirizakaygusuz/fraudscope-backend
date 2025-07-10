package com.finscope.fraudscope.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class TransactionConfig {

	@Bean("defaultTransactionTemplate")
     TransactionTemplate defaultTransactionTemplate(PlatformTransactionManager manager) {
        return new TransactionTemplate(manager); 
    }

    @Bean("newTransactionTemplate")
     TransactionTemplate newTransactionTemplate(PlatformTransactionManager manager) {
        TransactionTemplate transactionTemplate= new TransactionTemplate(manager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate;
    }
}
