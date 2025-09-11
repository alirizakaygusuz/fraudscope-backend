package com.finscope.fraudscope.kafka.dlq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailDlqConsumer {

	@KafkaListener(topics = "${app.kafka.auth.topic.verification-mail-dlt}", groupId = "${app.kafka.auth.consumer.dlt-verification-group-id}", containerFactory = "dltKafkaListenerContainerFactory")
	public void consumeVerificationMailDlq(ConsumerRecord<String, Object> record) {
		log.info("[DLQ] Verification Mail DLT message received.");
		logErrorDetails(record);
	}

	// json-byte Array
	@KafkaListener(topics = "${app.kafka.auth.topic.otp-mail-dlt}", groupId = "${app.kafka.auth.consumer.dlt-otp-group-id}", containerFactory = "dltKafkaListenerContainerFactory")
	public void consumeOtpMailDlq(ConsumerRecord<String, Object> record) {
		log.info("[DLQ] OTP Mail DLT message received.");
		logErrorDetails(record);
	}
	private void logErrorDetails(ConsumerRecord<String, Object> record) {
		log.error("[DLQ] Key: {}", record.key());
		log.error("[DLQ] Value (raw): {}", record.value());
		log.error("[DLQ] Partition: {}, Offset: {}", record.partition(), record.offset());
	}

}
