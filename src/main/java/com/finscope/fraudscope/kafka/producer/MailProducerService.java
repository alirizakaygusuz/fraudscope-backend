package com.finscope.fraudscope.kafka.producer;

import com.finscope.fraudscope.kafka.dto.KafkaMailPayload;

public interface MailProducerService {

	void sendVerificationToken(KafkaMailPayload mailPayload);

	void sendOtpToken(KafkaMailPayload mailPayload);

}
