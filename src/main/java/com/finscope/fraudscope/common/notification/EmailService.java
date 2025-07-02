package com.finscope.fraudscope.common.notification;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendVerificationEmail(VerificationToken verificationToken) {
    	
    	String toEmail = verificationToken.getAuthUser().getEmail();
    	TokenPurpose purpose = verificationToken.getTokenPurpose();
    	
        String subject = purpose.getTitle();
        String path = purpose.getPath();
        String message = purpose.getDescription();
        sendEmail(toEmail, verificationToken.getVerificationToken(), subject, path, message);
    }

    private void sendEmail(String toEmail, String token, String subject, String path, String message) {
        try {
            String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(path)
                    .queryParam("token", token)
                    .toUriString();

            String content = """
                <div style="text-align: center;">
                    <h3>%s</h3>
                    <p>%s</p>
                    <a href="%s" style="padding:10px; background-color:blue; color:white;">Verify Email</a>
                    <p>If you can't click, copy this URL: %s</p>
                </div>
                """.formatted(subject, message, actionUrl, actionUrl);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom("no-reply."+fromMail);
            helper.setText(content, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}

