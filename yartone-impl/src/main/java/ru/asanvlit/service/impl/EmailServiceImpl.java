package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.asanvlit.model.AccountEntity;
import ru.asanvlit.service.EmailService;
import ru.asanvlit.service.EmailGenerator;
import ru.asanvlit.service.EmailSender;

import java.util.Map;
import java.util.UUID;

import static java.util.Map.entry;
import static ru.asanvlit.constant.YartoneImplConstants.RESET_PASSWORD_EMAIL_TITLE;
import static ru.asanvlit.constant.YartoneImplConstants.SIGN_UP_EMAIL_TITLE;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailGenerator emailGenerator;

    private final EmailSender emailSender;

    @Override
    public void sendAccountConfirmationEmail(AccountEntity account, UUID accountConfirmCode) {
        Map<Object, Object> data = Map.ofEntries(
                entry("username", account.getUsername()),
                entry("confirm_code", accountConfirmCode)
        );

        String generatedEmail = emailGenerator.generateMail("account_confirm_mail.ftlh", data);
        emailSender.sendEmail(account.getEmail(), SIGN_UP_EMAIL_TITLE, generatedEmail);
    }

    @Override
    public void sendPasswordResetEmail(AccountEntity account, UUID passwordResetCode) {
        Map<Object, Object> data = Map.ofEntries(
                entry("username", account.getUsername()),
                entry("confirm_code", passwordResetCode)
        );

        String generatedEmail = emailGenerator.generateMail("password_reset_mail.ftlh", data);
        emailSender.sendEmail(account.getEmail(), RESET_PASSWORD_EMAIL_TITLE, generatedEmail);
    }
}
