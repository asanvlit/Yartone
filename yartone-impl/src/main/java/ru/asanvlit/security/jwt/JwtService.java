package ru.asanvlit.security.jwt;

import ru.asanvlit.dto.request.TokenPairRequest;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.dto.response.AccountResponse;

import java.util.UUID;

public interface JwtService {

    AccessRefreshTokenPairResponse getAccessRefreshTokenPair(UUID accountId);

    AccessRefreshTokenPairResponse refreshAccessToken(TokenPairRequest tokenPairRequest);

    AccountResponse getUserInfoByToken(String token);

    void deleteAccountsRefreshToken(UUID accountId);
}

