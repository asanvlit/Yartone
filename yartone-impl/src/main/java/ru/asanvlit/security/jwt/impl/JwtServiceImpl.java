package ru.asanvlit.security.jwt.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.request.TokenPairRequest;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.model.AccountRefreshTokenEntity;
import ru.asanvlit.security.jwt.JwtService;
import ru.asanvlit.security.provider.JwtAccessTokenProvider;
import ru.asanvlit.security.provider.JwtRefreshTokenProvider;
import ru.asanvlit.service.AccountService;
import ru.asanvlit.util.request.JwtRequestUtil;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final AccountService accountService;

    private final JwtAccessTokenProvider jwtAccessTokenProvider;

    private final JwtRefreshTokenProvider jwtRefreshTokenProvider;

    @Override
    public AccessRefreshTokenPairResponse getAccessRefreshTokenPair(UUID accountId) {
        String accessToken = jwtAccessTokenProvider.generateAccessToken(accountService.getAccountById(accountId));

        return AccessRefreshTokenPairResponse.builder()
                .accessToken(JwtRequestUtil.getFormattedAccessToken(accessToken))
                .refreshToken(jwtRefreshTokenProvider.generateRefreshToken(accountId).getId().toString())
                .accessTokenExpirationDate(jwtAccessTokenProvider.getExpirationDateFromAccessToken(accessToken))
                .build();
    }

    @Override
    public AccessRefreshTokenPairResponse refreshAccessToken(TokenPairRequest tokenPairRequest) {
        String role = jwtAccessTokenProvider.getRoleFromAccessToken(JwtRequestUtil.getToken(tokenPairRequest.getAccessToken()));

        AccountRefreshTokenEntity refreshToken = jwtRefreshTokenProvider.verifyRefreshTokenExpiration(
                tokenPairRequest.getRefreshToken(), role);
        String accessToken = jwtAccessTokenProvider.generateAccessToken(refreshToken.getAccount());

        return AccessRefreshTokenPairResponse.builder()
                .refreshToken(String.valueOf(refreshToken.getId()))
                .accessToken(JwtRequestUtil.getFormattedAccessToken(accessToken))
                .accessTokenExpirationDate(jwtAccessTokenProvider.getExpirationDateFromAccessToken(accessToken))
                .build();
    }

    @Override
    public AccountResponse getUserInfoByToken(String token) {
        return jwtAccessTokenProvider.getUserInfoByToken(token);
    }

    @Override
    public void deleteAccountsRefreshToken(UUID accountId) {
        jwtRefreshTokenProvider.deleteAccountsRefreshToken(accountId);
    }
}

