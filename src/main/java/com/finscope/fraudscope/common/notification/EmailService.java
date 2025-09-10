package com.finscope.fraudscope.common.notification;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.finscope.fraudscope.authentication.verification.MailTokenPayload;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;
import com.finscope.fraudscope.kafka.dto.KafkaMailPayload;
import com.finscope.fraudscope.kafka.producer.MailProducerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final MailProducerService mailProducerService;

	
	public void sendAccountVerificationEmail(VerificationToken verificationToken) {
	  KafkaMailPayload kafkaMailPayload = convertToKafkaMailPayload(verificationToken);
	  mailProducerService.sendVerificationToken(kafkaMailPayload);
	}

	public void sendOtpCodeEmail(OtpToken otpToken) {
		 KafkaMailPayload kafkaMailPayload = convertToKafkaMailPayload(otpToken);
		 mailProducerService.sendOtpToken(kafkaMailPayload);
	}
	
	
	private KafkaMailPayload convertToKafkaMailPayload(MailTokenPayload payload) {
		String toEmail = payload.getRecipientEmail();
		TokenPurpose purpose = payload.getTokenPurpose();

		String subject = purpose.getTitle();
		String token = payload.getTokenValue();

		String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(purpose.getPath())
				.queryParam("token", token).toUriString();

		String content = buildContent(payload, actionUrl);
		
		return KafkaMailPayload.builder()
				.subject(subject)
				.toEmail(toEmail)
				.content(content)
				.build();
		
		
	}
	
	private String buildContent(MailTokenPayload payload, String actionUrl) {
		String subject = payload.getTokenPurpose().getTitle();
		String message = payload.getTokenPurpose().getDescription();

		if (payload.getTokenPurpose() == TokenPurpose.TWO_FACTOR_AUTH) {
			return String.format("""
					<div style="text-align: center;">
					    <h3>%s</h3>
					    <p>%s</p>
					    <h2 style="font-size: 32px; letter-spacing: 4px;">%s</h2>
					   	<p>This code will expire in 3 minutes.</p>
					</div>
					""", subject, message, payload.getTokenValue());
		}

		return String.format("""
				<div style="text-align: center;">
				    <h3>%s</h3>
				    <p>%s</p>
				    <a href="%s" style="padding:10px; background-color:blue; color:white;">Verify Email</a>
				    <p>If you can't click, copy this URL: %s</p>
				</div>
				""", subject, message, actionUrl, actionUrl);
	}


// LEGACY - SMTP Email
//		private final JavaMailSender mailSender;
//
// 		@Value("${spring.mail.username}")
//		private String fromMail;
//
//	private void sendTokenEmail(MailTokenPayload payload) {
//		String toEmail = payload.getRecipientEmail();
//		TokenPurpose purpose = payload.getTokenPurpose();
//
//		String subject = purpose.getTitle();
//		String token = payload.getTokenValue();
//
//		String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(purpose.getPath())
//				.queryParam("token", token).toUriString();
//
//		String content = buildContent(payload, actionUrl);
//
//		sendEmail(toEmail, subject, content);
//	}
//
//	private void sendEmail(String toEmail, String subject, String content) {
//		try {
//			MimeMessage mimeMessage = mailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//			helper.setTo(toEmail);
//			helper.setSubject(subject);
//			helper.setFrom("no-reply" + fromMail);
//			helper.setText(content, true);
//
//			mailSender.send(mimeMessage);
//		} catch (Exception e) {
//			throw new BaseException(new ErrorMessage(ErrorType.EMAIL_SENDING_FAILED));
//		}
//	}

	

}
