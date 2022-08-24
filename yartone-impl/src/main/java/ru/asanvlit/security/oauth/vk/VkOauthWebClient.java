package ru.asanvlit.security.oauth.vk;

import ru.asanvlit.dto.response.vk.VkAccountAccessTokenResponse;
import ru.asanvlit.dto.response.vk.VkAccountResponse;

public interface VkOauthWebClient {

    String generateInvitationLink();

    VkAccountAccessTokenResponse getOauthToken(String code);

    VkAccountResponse getVkAccountInfo(Long vkAccountId, String accessToken);
}
