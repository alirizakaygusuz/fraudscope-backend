package com.finscope.fraudscope.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class KafkaMailPayload {
	
	private String toEmail;
	private String subject;
	private String content;

}
