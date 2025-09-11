package com.finscope.fraudscope.kafka.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.util.backoff.FixedBackOff;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaErrorHandlerConfig {

	@Bean
	DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaTemplate<Object, Object> template) {
		DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template, (record, ex) -> {
			
			String topic = record.topic();
			if (topic.endsWith(".DLT")) {
				log.info("[DLT Recoverer] Already in DLT -> {}", topic);
				return null;
			}
			
			log.info("[DLT Recoverer] Redirecting {} -> {}.DLT", topic, topic);
			return new TopicPartition(topic + ".DLT", record.partition());
		});
		recoverer.setFailIfSendResultIsError(false);
		
		return recoverer;
	}

	@Bean
	DefaultErrorHandler errorHandler(DeadLetterPublishingRecoverer recoverer) {
		// Retry every 1s -> max attempts 3
		log.info("[DLT ErrorHandler] -> .DLT");
		DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 3));

		errorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
			log.warn("[Retry] attempt {} for key: {}", deliveryAttempt, record.key());
		});
		
		return errorHandler;
	}

}
