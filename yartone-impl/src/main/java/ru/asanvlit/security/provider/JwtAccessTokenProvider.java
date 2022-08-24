package ru.asanvlit.security.provider;

import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.model.AccountEntity;

import java.util.Date;

public interface JwtAccessTokenProvider {

    String generateAccessToken(AccountEntity account);

    Date getExpirationDateFromAccessToken(String accessToken);

    boolean isExpiredAccessToken(String accessToken);

    String getRoleFromAccessToken(String accessToken);

    String getEmailFromAccessToken(String accessToken);

    String getSubjectFromAccessToken(String accessToken);

    AccountResponse getUserInfoByToken(String token);
}
