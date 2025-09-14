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
	@KafkaListener(topics = "${app.kafka.auth.topic.verification-mail}" , groupId = "${app.kafka.auth.consumer.auth-group-id}")
	public void consumeVerificationTokenMailEvent(ConsumerRecord<String, KafkaMailPayload> event) {	
		logAndDispatch("Verifciation Token", event);
	}

	@Override
	@KafkaListener(topics = "${app.kafka.auth.topic.otp-mail}" , groupId = "${app.kafka.auth.consumer.auth-group-id}")
	public void consumeOtpTokenMailEvent(ConsumerRecord<String, KafkaMailPayload> event) {
		logAndDispatch("OTP", event);
	}

	@Override
	@KafkaListener(topics = "${app.kafka.auth.topic.rate-limit-mail}" , groupId = "${app.kafka.auth.consumer.ratelimit-group-id}")
	public void consumeRateLimitMailEvent(ConsumerRecord<String, KafkaMailPayload> event) {
		logAndDispatch("RateLimit", event);
	}
	
	private void logAndDispatch(String type  ,ConsumerRecord<String, KafkaMailPayload> event ) {
		KafkaMailPayload payload = event.value();
		log.info(" [Kafka-{}] Received {} mail  key {} | payload:{} ",type,type,event.key(),payload);
		mailDispatcherService.sendEmail(payload);

	}

}
