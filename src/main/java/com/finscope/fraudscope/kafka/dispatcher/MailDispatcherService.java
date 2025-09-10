package com.finscope.fraudscope.kafka.dispatcher;

import com.finscope.fraudscope.kafka.dto.KafkaMailPayload;

public interface MailDispatcherService {
	void sendEmail(KafkaMailPayload mailPayload);
}
