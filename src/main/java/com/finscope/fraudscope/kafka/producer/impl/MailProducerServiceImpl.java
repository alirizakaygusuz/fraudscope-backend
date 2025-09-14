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
	
	
	@Value("${app.kafka.auth.topic.rate-limit-mail}")
	private String rateLimitTopic;
	
	@Override
	public void sendVerificationToken(KafkaMailPayload mailPayload) {
		//topic , key, value
		kafkaTemplate.send(verificationMailTopic,mailPayload.getToEmail(), mailPayload);

	}

	@Override
	public void sendOtpToken(KafkaMailPayload mailPayload) {
		kafkaTemplate.send(otpMailTopic,mailPayload.getToEmail(), mailPayload);

	}

	

	@Override
	public void sendRateLimitWarningMail(KafkaMailPayload mailPayload) {
		kafkaTemplate.send(rateLimitTopic,mailPayload.getToEmail(), mailPayload);
		
	}

}
