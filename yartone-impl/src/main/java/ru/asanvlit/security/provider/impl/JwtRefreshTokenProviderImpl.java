package ru.asanvlit.security.provider.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.asanvlit.exception.base.YartoneBadRequestException;
import ru.asanvlit.exception.base.YartoneRefreshTokenException;
import ru.asanvlit.exception.illegalstate.YartoneRefreshTokenStateException;
import ru.asanvlit.exception.notfound.YartoneRefreshTokenNotFoundException;
import ru.asanvlit.model.AccountRefreshTokenEntity;
import ru.asanvlit.model.RefreshTokenEntity;
import ru.asanvlit.repository.postgres.AccountRefreshTokenRepository;
import ru.asanvlit.security.provider.JwtRefreshTokenProvider;
import ru.asanvlit.service.AccountService;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtRefreshTokenProviderImpl implements JwtRefreshTokenProvider {

    private final AccountService accountService;

    private final AccountRefreshTokenRepository accountRefreshTokenRepository;

    @Value("${jwt.expiration.refresh.mills}")
    private long expirationRefreshInMillis;

    @Override
    @Transactional
    public AccountRefreshTokenEntity generateRefreshToken(UUID accountId) {
        Optional<AccountRefreshTokenEntity> optionalRefreshToken = accountRefreshTokenRepository.findByAccount_Id(accountId);
        if (optionalRefreshToken.isPresent() && !isRefreshTokenExpired(optionalRefreshToken.get())) {
            throw new YartoneRefreshTokenStateException("There is already not expired refresh token for the account");
        }

        return accountRefreshTokenRepository.save(
                AccountRefreshTokenEntity.builder()
                        .expiryDate(Instant.now().plusMillis(expirationRefreshInMillis))
                        .account(accountService.getAccountById(accountId))
                        .build()
        );
    }

    @Override
    public AccountRefreshTokenEntity verifyRefreshTokenExpiration(String refreshToken, String role) {
        try {
            return accountRefreshTokenRepository.findById(UUID.fromString(refreshToken)).map(token -> {
                accountRefreshTokenRepository.delete(token);

                if (isRefreshTokenExpired(token)) {
                    throw new YartoneRefreshTokenException(String.valueOf(token.getId()), "The refresh token has expired");
                }

                return generateRefreshToken(token.getAccount().getId());
            }).orElseThrow(() -> {
                throw new YartoneRefreshTokenException(refreshToken, "This token does not exist");
            });
        } catch (IllegalArgumentException e) {
            throw new YartoneBadRequestException(e.getMessage());
        }
    }

    @Override
    public boolean isRefreshTokenExpired(RefreshTokenEntity refreshToken) {
        return refreshToken.getExpiryDate().compareTo(Instant.now()) < 0;
    }

    @Override
    public void deleteAccountsRefreshToken(UUID accountId) {
        Optional<AccountRefreshTokenEntity> refreshToken = accountRefreshTokenRepository.findByAccount_Id(accountId);
        if (refreshToken.isPresent()) {
            accountRefreshTokenRepository.delete(refreshToken.get());
        } else {
            throw new YartoneRefreshTokenNotFoundException();
        }
    }
}

