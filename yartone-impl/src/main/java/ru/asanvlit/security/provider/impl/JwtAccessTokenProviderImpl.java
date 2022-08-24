package ru.asanvlit.security.provider.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.exception.unauthorized.YartoneAuthenticationHeaderException;
import ru.asanvlit.exception.unauthorized.YartoneAuthenticationSchemeException;
import ru.asanvlit.exception.unauthorized.YartoneJwtExpiredException;
import ru.asanvlit.model.AccountEntity;
import ru.asanvlit.security.provider.JwtAccessTokenProvider;
import ru.asanvlit.service.AccountService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static ru.asanvlit.constant.YartoneImplConstants.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAccessTokenProviderImpl implements JwtAccessTokenProvider {

    private final ObjectMapper objectMapper;

    private final AccountService accountService;

    @Value("${jwt.expiration.access.millis}")
    private long expirationAccessInMillis;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public String generateAccessToken(AccountEntity account) {
        return JWT.create()
                .withSubject(account.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationAccessInMillis))
                .withClaim(ROLE_CLAIM, account.getRole().name())
                .withClaim(EMAIL_CLAIM, account.getEmail())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public Date getExpirationDateFromAccessToken(String accessToken) {
        try {
            return getDecodedJWT(accessToken).getExpiresAt();
        } catch (TokenExpiredException ex) {
            return new Date(Long.parseLong(String.valueOf(getDecodedPayload(accessToken).get(DECODED_EXPIRES_PARAM))));
        }
    }

    @Override
    public boolean isExpiredAccessToken(String accessToken) {
        return !getExpirationDateFromAccessToken(accessToken).after(new Date());
    }

    @Override
    public String getRoleFromAccessToken(String accessToken) {
        try {
            return getDecodedJWT(accessToken).getClaims().get(ROLE_CLAIM).asString();
        } catch (TokenExpiredException ex) {
            return String.valueOf(getDecodedPayload(accessToken).get(DECODED_ROLE_PARAM));
        }
    }

    @Override
    public String getEmailFromAccessToken(String accessToken) {
        try {
            return getDecodedJWT(accessToken).getClaims().get(EMAIL_CLAIM).asString();
        } catch (TokenExpiredException ex) {
            return String.valueOf(getDecodedPayload(accessToken).get(DECODED_EMAIL_PARAM));
        }
    }

    @Override
    public String getSubjectFromAccessToken(String accessToken) {
        try {
            return getDecodedJWT(accessToken).getSubject();
        } catch (TokenExpiredException ex) {
            return String.valueOf(getDecodedPayload(accessToken).get(DECODED_SUBJECT_PARAM));
        }
    }

    @Override
    public AccountResponse getUserInfoByToken(String token) {
        if (!isExpiredAccessToken(token)) {
            return accountService.findBySubject(getSubjectFromAccessToken(token))
                    .orElseThrow(() -> new YartoneAuthenticationHeaderException("User with this email wasn't found"));
        } else {
            throw new YartoneJwtExpiredException("Access token expired");
        }
    }

    private DecodedJWT getDecodedJWT(String accessToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();

            return jwtVerifier.verify(accessToken);
        } catch (SignatureVerificationException ex) {
            throw new YartoneAuthenticationSchemeException("The Token's Signature resulted invalid");
        }
    }

    private Map<?, ?> getDecodedPayload(String accessToken) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] parts = accessToken.split("\\.");
        try {
            return objectMapper.readValue(decoder.decode(parts[1]), Map.class);
        } catch (IOException e) {
            log.warn("Failed to get decoded payload from token due to: {}", e.getMessage());
            throw new YartoneAuthenticationSchemeException("Can't authenticate with this token");
        }
    }
}

