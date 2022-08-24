package ru.asanvlit.security.oauth.vk;

import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.model.AccountEntity;

import java.util.UUID;

public interface VkOauthService {

    Boolean isVkAuthorizedAccount(UUID accountId);

    AccountEntity getVkAccount(String code);

    AccessRefreshTokenPairResponse loginWithOauth(UUID accountId, String code);

    String getLoginLink();
}
