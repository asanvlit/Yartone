package ru.asanvlit.security.oauth.vk;

import ru.asanvlit.model.VkOauthAccountAccessTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface VkOauthTokenService {

    UUID saveAccountAccessToken(VkOauthAccountAccessTokenEntity vkOauthAccountAccessToken);

    Optional<VkOauthAccountAccessTokenEntity> getAccountAccessTokenByAccountId(UUID accountId);

    Boolean existsVkAccountAccessTokenByAccountId(UUID accountId);
}
