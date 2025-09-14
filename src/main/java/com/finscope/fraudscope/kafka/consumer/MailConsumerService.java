package com.finscope.fraudscope.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.finscope.fraudscope.kafka.dto.KafkaMailPayload;

public interface MailConsumerService {

	void consumeVerificationTokenMailEvent(ConsumerRecord<String, KafkaMailPayload> event);

	void consumeOtpTokenMailEvent(ConsumerRecord<String, KafkaMailPayload> event);
	
	void consumeRateLimitMailEvent(ConsumerRecord<String, KafkaMailPayload> event);

}
