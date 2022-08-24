package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import ru.asanvlit.service.EmailSender;

@RequiredArgsConstructor
@Component
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String to, String subject, String emailText) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailText, true);
            messageHelper.setTo(to);
            messageHelper.setFrom(from);
        };

        emailSender.send(preparator);
    }
}

