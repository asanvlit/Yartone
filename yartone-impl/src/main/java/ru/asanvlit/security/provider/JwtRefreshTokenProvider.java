package ru.asanvlit.security.provider;

import ru.asanvlit.model.AccountRefreshTokenEntity;
import ru.asanvlit.model.RefreshTokenEntity;

import java.util.UUID;

public interface JwtRefreshTokenProvider {

    AccountRefreshTokenEntity generateRefreshToken(UUID accountId);

    AccountRefreshTokenEntity verifyRefreshTokenExpiration(String refreshToken, String role);

    boolean isRefreshTokenExpired(RefreshTokenEntity refreshToken);

    void deleteAccountsRefreshToken(UUID accountId);
}
