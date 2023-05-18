package com.example.travel_agency_pfe.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("mailService")
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("lamaachi.officiel@gmail.com\n");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content);

        mailSender.send(message);
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }
}
