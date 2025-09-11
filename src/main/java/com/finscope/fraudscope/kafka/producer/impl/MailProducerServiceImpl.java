package com.finscope.fraudscope.kafka.producer.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.kafka.dto.KafkaMailPayload;
import com.finscope.fraudscope.kafka.producer.MailProducerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailProducerServiceImpl implements MailProducerService {

	private final KafkaTemplate<String, KafkaMailPayload> kafkaTemplate;

	@Value("${app.kafka.auth.topic.verification-mail}")
	private String verificationMailTopic;

	@Value("${app.kafka.auth.topic.otp-mail}")
	private String otpMailTopic;
	
	@Override
	public void sendVerificationToken(KafkaMailPayload mailPayload) {
		String key = mailPayload.getToEmail();
		//topic , key, value
		kafkaTemplate.send(verificationMailTopic,key, mailPayload);

	}

	@Override
	public void sendOtpToken(KafkaMailPayload mailPayload) {
		String key = mailPayload.getToEmail();
		kafkaTemplate.send(otpMailTopic,key, mailPayload);

	}

}
