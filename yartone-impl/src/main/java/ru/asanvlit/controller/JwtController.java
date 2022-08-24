package ru.asanvlit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.asanvlit.api.JwtApi;
import ru.asanvlit.dto.request.TokenPairRequest;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.security.jwt.JwtService;

@RequiredArgsConstructor
@RestController
public class JwtController implements JwtApi {

    private final JwtService jwtService;

    @Override
    public AccessRefreshTokenPairResponse refreshTokens(TokenPairRequest tokenPairRequest) {
        return jwtService.refreshAccessToken(tokenPairRequest);
    }

    @Override
    public AccountResponse userInfoByToken(String token) {
        return jwtService.getUserInfoByToken(token);
    }
}
