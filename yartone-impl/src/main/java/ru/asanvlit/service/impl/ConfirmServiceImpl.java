package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.AccountState;
import ru.asanvlit.dto.enums.ConfirmCodeType;
import ru.asanvlit.dto.request.EmailRequest;
import ru.asanvlit.dto.request.PasswordCreationRequest;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.exception.base.YartoneMethodNotAllowedException;
import ru.asanvlit.exception.illegalargument.YartoneIllegalConfirmCodeException;
import ru.asanvlit.exception.illegalstate.YartoneAlreadyExistingConfirmCodeException;
import ru.asanvlit.exception.illegalstate.YartoneExpiredConfirmCodeException;
import ru.asanvlit.exception.illegalstate.YartoneIllegalAccountStateException;
import ru.asanvlit.exception.notfound.YartoneConfirmCodeNotFoundException;
import ru.asanvlit.model.AccountEntity;
import ru.asanvlit.model.ConfirmCodeEntity;
import ru.asanvlit.repository.postgres.ConfirmCodeRepository;
import ru.asanvlit.security.jwt.JwtService;
import ru.asanvlit.service.AccountService;
import ru.asanvlit.service.ConfirmCodeTypeService;
import ru.asanvlit.service.ConfirmService;
import ru.asanvlit.service.EmailService;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static ru.asanvlit.constant.YartoneImplConstants.CODES_ENCODING_ALG;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConfirmServiceImpl implements ConfirmService {

    private final JwtService jwtService;

    private final AccountService accountService;

    private final ConfirmCodeTypeService confirmCodeTypeService;

    private final EmailService emailService;

    private final ConfirmCodeRepository confirmCodeRepository;

    @Value("${confirm-code.secret}")
    private String secretKey;

    @Override
    public UUID generateConfirmCodeForAccount(UUID accountId, ConfirmCodeType codeType) {
        if (!existsValidAccountsCodeWithType(accountId, codeType)) {
            UUID codeValue = generateConfirmCodeValue();
            confirmCodeRepository.save(ConfirmCodeEntity.builder()
                    .account(accountService.getAccountById(accountId))
                    .code(getEncodedConfirmCodeValue(codeValue))
                    .confirmCodeType(confirmCodeTypeService.getConfirmCodeTypeByType(codeType))
                    .createDate(Instant.now())
                    .build());
            return codeValue;
        } else {
            throw new YartoneAlreadyExistingConfirmCodeException();
        }
    }

    @Override
    public void sendAccountConfirm(UUID accountId, UUID confirmCodeValue) {
        emailService.sendAccountConfirmationEmail(accountService.getAccountById(accountId), confirmCodeValue);
    }

    @Override
    public Optional<ConfirmCodeEntity> findAccountsConfirmCode(UUID accountId, ConfirmCodeType codeType) {
        return confirmCodeRepository.findByAccount_IdAndConfirmCodeType_ConfirmCodeType(accountId, codeType);
    }

    public ConfirmCodeEntity getConfirmCodeById(UUID id) {
        return confirmCodeRepository.findById(id).orElseThrow(YartoneConfirmCodeNotFoundException::new);
    }

    @Transactional
    @Override
    public AccessRefreshTokenPairResponse confirmAccount(UUID confirmCode, PasswordCreationRequest passwordCreationRequest) {
        return confirmCodeRepository.findByCodeAndConfirmCodeType_ConfirmCodeType(getEncodedConfirmCodeValue(confirmCode), ConfirmCodeType.ACCOUNT_CONFIRM)
                .map(c -> {
                    if (isActiveCode(c)) {
                        accountService.changeAccountsPassword(c.getAccount().getId(), passwordCreationRequest.getPassword());
                        accountService.changeAccountsState(c.getAccount().getId(), AccountState.ACTIVE);
                        confirmCodeRepository.delete(c);

                        return jwtService.getAccessRefreshTokenPair(c.getAccount().getId());
                    } else {
                        throw new YartoneExpiredConfirmCodeException();
                    }
                })
                .orElseThrow(() -> new YartoneIllegalConfirmCodeException("No such confirm code"));
    }

    @Override
    public void resendEmailAccountConfirmCode(EmailRequest emailRequest) {
        AccountEntity account = accountService.getAccountByEmail(emailRequest.getEmail());

        if (!AccountState.CREATED.equals(account.getState())) {
            throw new YartoneIllegalAccountStateException("The 'CREATED' account's state is required to perform");
        }

        confirmCodeRepository.findByAccount_IdAndConfirmCodeType_ConfirmCodeType(account.getId(), ConfirmCodeType.ACCOUNT_CONFIRM)
                .map(c -> {
                    if (isActiveCode(c)) {
                        confirmCodeRepository.delete(c);
                    } else {
                        throw new YartoneExpiredConfirmCodeException("Account confirmation time expired");
                    }
                    return c;
                });

        UUID newConfirmCodeValue = generateConfirmCodeForAccount(account.getId(), ConfirmCodeType.ACCOUNT_CONFIRM);
        sendAccountConfirm(account.getId(), newConfirmCodeValue);
    }

    @Override
    public void resetPassword(EmailRequest emailRequest) {
        AccountEntity account = accountService.getAccountByEmail(emailRequest.getEmail());

        if (!AccountState.ACTIVE.equals(account.getState())) {
            throw new YartoneIllegalAccountStateException("The 'ACTIVE' account's state is required to perform");
        }

        if (!existsValidAccountsCodeWithType(account.getId(), ConfirmCodeType.PASSWORD_RESET)) {
            UUID passwordResetCode = generateConfirmCodeForAccount(account.getId(), ConfirmCodeType.PASSWORD_RESET);
            emailService.sendPasswordResetEmail(account, passwordResetCode);
        } else {
            throw new YartoneAlreadyExistingConfirmCodeException();
        }
    }

    @Override
    public AccessRefreshTokenPairResponse createPasswordAfterReset(UUID confirmCode, PasswordCreationRequest passwordCreationRequest) {
        return confirmCodeRepository.findByCodeAndConfirmCodeType_ConfirmCodeType(getEncodedConfirmCodeValue(confirmCode), ConfirmCodeType.PASSWORD_RESET)
                .map(c -> {
                    if (isActiveCode(c)) {
                        accountService.changeAccountsPassword(c.getAccount().getId(), passwordCreationRequest.getPassword());
                        confirmCodeRepository.delete(c);
                        jwtService.deleteAccountsRefreshToken(c.getAccount().getId());

                        return jwtService.getAccessRefreshTokenPair(c.getAccount().getId());
                    } else {
                        throw new YartoneExpiredConfirmCodeException();
                    }
                })
                .orElseThrow(() -> new YartoneIllegalConfirmCodeException("No such confirm code"));
    }

    @Override
    public Set<ConfirmCodeEntity> getExpiredConfirmCodes() {
        return confirmCodeRepository.getExpiredConfirmCodes();
    }

    @Override
    public void deleteConfirmCode(UUID id) {
        confirmCodeRepository.delete(getConfirmCodeById(id));
    }

    private UUID generateConfirmCodeValue() {
        return UUID.randomUUID();
    }

    private String getEncodedConfirmCodeValue(UUID codeValue) {
        try {
            Mac mac = Mac.getInstance(CODES_ENCODING_ALG);
            mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), CODES_ENCODING_ALG));
            return Hex.encodeHexString(mac.doFinal(String.valueOf(codeValue).getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new YartoneMethodNotAllowedException("Failed to encode confirm code's value");
        }
    }

    private boolean existsValidAccountsCodeWithType(UUID accountId, ConfirmCodeType codeType) {
        Optional<ConfirmCodeEntity> confirmCode = findAccountsConfirmCode(accountId, codeType);
        return confirmCode.isPresent() && isActiveCode(confirmCode.get());
    }

    private boolean isActiveCode(ConfirmCodeEntity code) {
        return Duration.between(code.getCreateDate(), Instant.now()).toHours() <= code.getConfirmCodeType().getValidTime();
    }
}
