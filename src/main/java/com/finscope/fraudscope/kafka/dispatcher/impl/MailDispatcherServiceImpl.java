package com.finscope.fraudscope.kafka.dispatcher.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;
import com.finscope.fraudscope.kafka.dispatcher.MailDispatcherService;
import com.finscope.fraudscope.kafka.dto.KafkaMailPayload;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailDispatcherServiceImpl implements MailDispatcherService{

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromMail;

	@Override
	public void sendEmail(KafkaMailPayload mailPayload) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(mailPayload.getToEmail());
			helper.setSubject(mailPayload.getSubject());
			helper.setFrom("no-reply" + fromMail);
			helper.setText(mailPayload.getContent(), true);

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new BaseException(new ErrorMessage(ErrorType.EMAIL_SENDING_FAILED));
		}
	}




}
