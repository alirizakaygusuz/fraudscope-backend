package com.finscope.fraudscope.kafka.config;

import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaConsumerConfig {

    //Factory Configuraiton For Topic listener 
    @Bean
    ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConsumerFactory<Object, Object> consumerFactory,
            DefaultErrorHandler errorHandler,
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        configurer.configure(factory, consumerFactory); 
        factory.setCommonErrorHandler(errorHandler); 
        log.info("[Topic Factory] Created with DefaultErrorHandler");

        return factory;
    }
    //Factory Configuraiton For Topic.DLT listener 
    //No ErrorHandler -> This consumer procces already failed messages 
    @Bean(name = "dltKafkaListenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<Object, Object> dltKafkaListenerContainerFactory(
            ConsumerFactory<Object, Object> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        
        log.info("[DLT Factory] Created without DefaultErrorHandler");
        return factory;
    }
    
}
