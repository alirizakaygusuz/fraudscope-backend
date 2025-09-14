package com.finscope.fraudscope.kafka.dlq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailDlqConsumer {
	

	@KafkaListener(topics = "${app.kafka.auth.topic.verification-mail-dlt}", groupId = "${app.kafka.auth.consumer.dlt-verification-group-id}", containerFactory = "dltKafkaListenerContainerFactory")
	public void consumeVerificationMailDlq(ConsumerRecord<String, Object> event) {
		logErrorDetails("Verification Token",event);
	}

	// json-byte Array
	@KafkaListener(topics = "${app.kafka.auth.topic.otp-mail-dlt}", groupId = "${app.kafka.auth.consumer.dlt-otp-group-id}", containerFactory = "dltKafkaListenerContainerFactory")
	public void consumeOtpMailDlq(ConsumerRecord<String, Object> event) {
		logErrorDetails("OTP",event);
	}
	
	@KafkaListener(topics = "${app.kafka.auth.topic.ratelimit-mail-dlt}", groupId = "${app.kafka.auth.consumer.dlt-rate-limit-mail-group-id}", containerFactory = "dltKafkaListenerContainerFactory")
	public void consumeRatelimitMailDlq(ConsumerRecord<String, Object> event) {
		logErrorDetails("RateLimit",event);
	}
	
	private void logErrorDetails(String type,ConsumerRecord<String, Object> event) {
		log.info("[DLQ-{}] {} Mail DLT message received.",type,type);
		log.error("[DLQ] Key: {}", event.key());
		log.error("[DLQ] Value (raw): {}", event.value());
		log.error("[DLQ] Partition: {}, Offset: {}", event.partition(), event.offset());
	}

}
