package com.finscope.fraudscope.kafka.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.kafka.consumer.MailConsumerService;
import com.finscope.fraudscope.kafka.dispatcher.MailDispatcherService;
import com.finscope.fraudscope.kafka.dto.KafkaMailPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailConsumerServiceImpl implements MailConsumerService {

	private final MailDispatcherService mailDispatcherService;

	@Override
	@KafkaListener(topics = "${app.kafka.auth.topic.verification-mail}" , groupId = "${app.kafka.auth.consumer.group-id}")
	public void consumeVerificationTokenMailEvent(ConsumerRecord<String, KafkaMailPayload> record) {
		KafkaMailPayload payload = record.value();
		log.info(" [Kafka] Received verification mail payload: {}", payload);
		mailDispatcherService.sendEmail(payload);

	}

	@Override
	@KafkaListener(topics = "${app.kafka.auth.topic.otp-mail}" , groupId = "${app.kafka.auth.consumer.group-id}")
	public void consumeOtpTokenMailEvent(ConsumerRecord<String, KafkaMailPayload> record) {
		KafkaMailPayload payload = record.value();
		log.info(" [Kafka] Received OTP mail payload: {}", payload);
		mailDispatcherService.sendEmail(payload);

	}

}
