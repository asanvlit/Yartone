package ru.asanvlit.service;

import ru.asanvlit.dto.enums.ConfirmCodeType;
import ru.asanvlit.dto.request.EmailRequest;
import ru.asanvlit.dto.request.PasswordCreationRequest;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.model.ConfirmCodeEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ConfirmService {

    UUID generateConfirmCodeForAccount(UUID accountId, ConfirmCodeType confirmCodeType);

    void sendAccountConfirm(UUID accountId, UUID confirmCodeValue);

    Optional<ConfirmCodeEntity> findAccountsConfirmCode(UUID accountId, ConfirmCodeType codeType);

    AccessRefreshTokenPairResponse confirmAccount(UUID confirmCode, PasswordCreationRequest passwordCreationRequest);

    void resendEmailAccountConfirmCode(EmailRequest emailRequest);

    void resetPassword(EmailRequest emailRequest);

    AccessRefreshTokenPairResponse createPasswordAfterReset(UUID confirmCode, PasswordCreationRequest passwordCreationRequest);

    Set<ConfirmCodeEntity> getExpiredConfirmCodes();

    void deleteConfirmCode(UUID id);
}
