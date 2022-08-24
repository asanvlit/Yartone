package ru.asanvlit.security.oauth.vk.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.asanvlit.dto.response.vk.*;
import ru.asanvlit.exception.methodnotallowed.YartoneOauthException;
import ru.asanvlit.exception.notfound.YartoneAccountNotFoundException;
import ru.asanvlit.security.oauth.vk.VkOauthWebClient;
import ru.asanvlit.util.request.UriUtil;

import java.util.*;

import static ru.asanvlit.constant.YartoneImplConstants.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class VkOauthWebClientImpl implements VkOauthWebClient {

    private final WebClient webClient;

    @Value("${vk.oauth.version}")
    private String vkApiVersion;

    @Value("${vk.oauth.client.id}")
    private String vkClientId;

    @Value("${vk.oauth.client.secret}")
    private String vkClientSecret;

    @Value("${vk.oauth.redirect.url}")
    private String vkLoginRedirectUrl;

    @Value("${vk.oauth.api.token.url}")
    private String vkGettingTokenUrl;

    @Value("${vk.oauth.authorization.url}")
    private String vkAuthorizationUrl;

    @Override
    public String generateInvitationLink() {
        return UriUtil.buildRequest(vkAuthorizationUrl, buildInvitationParamMap());
    }

    @Override
    public VkAccountAccessTokenResponse getOauthToken(String code) {
        String uri = UriUtil.buildRequest(vkGettingTokenUrl, buildGetTokenParamMap(code));
        return (VkAccountAccessTokenResponse) sendSynchronizedRequest(uri, HttpMethod.GET, VkAccountAccessTokenResponse.class);
    }

    @Override
    public VkAccountResponse getVkAccountInfo(Long vkAccountId, String accessToken) {
        String uri = UriUtil.buildRequest(VK_GET_USER_INFO_URL, buildGetAccountParamMap(vkAccountId, accessToken));

        VkAccountsResponse vkAccountsResponse = (VkAccountsResponse) sendSynchronizedRequest(uri, HttpMethod.GET,
                VkAccountsResponse.class);
        if (Objects.nonNull(vkAccountsResponse)) {
            return vkAccountsResponse.getResponse().stream()
                    .findFirst()
                    .orElseThrow(() -> new YartoneAccountNotFoundException("Vk account not found"));
        } else {
            throw new YartoneOauthException("Response from vk is null");
        }
    }

    private Object sendSynchronizedRequest(String uri, HttpMethod httpMethod, Class<?> responseClass) {
        try {
            return webClient
                    .method(httpMethod)
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(responseClass)
                    .block();
        } catch (WebClientResponseException | WebClientRequestException e) {
            log.warn("Failed to execute due to the: {} - {}", e.getClass().getSimpleName(), e.getMessage());
            throw new YartoneOauthException(String.format("Failed to execute due to the: [%s]", e.getMessage()));
        }
    }

    private Map<String, String> buildGetTokenParamMap(String code) {
        return new HashMap<>() {{
            put("client_id", vkClientId);
            put("client_secret", vkClientSecret);
            put("redirect_uri", vkLoginRedirectUrl);
            put("code", code);
        }};
    }

    private Map<String, String> buildInvitationParamMap() {
        return new HashMap<>() {{
            put("client_id", vkClientId);
            put("redirect_uri", vkLoginRedirectUrl);
            put("response_type", "code");
            put("scope", "email,offline,groups,wall");
            put("v", vkApiVersion);
        }};
    }

    private Map<String, String> buildGetAccountParamMap(Long vkAccountId, String accessToken) {
        return new HashMap<>() {{
            put("v", vkApiVersion);
            put("uids", vkAccountId.toString());
            put("access_token", accessToken);
        }};
    }
}

