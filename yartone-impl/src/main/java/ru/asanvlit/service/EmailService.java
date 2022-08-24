package ru.asanvlit.service;

import ru.asanvlit.model.AccountEntity;

import java.util.UUID;

public interface EmailService {

    void sendAccountConfirmationEmail(AccountEntity account, UUID accountConfirmCode);

    void sendPasswordResetEmail(AccountEntity account, UUID passwordResetCode);
}
