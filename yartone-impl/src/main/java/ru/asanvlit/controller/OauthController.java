package ru.asanvlit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import ru.asanvlit.api.OauthApi;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.security.details.AccountUserDetailsImpl;
import ru.asanvlit.security.oauth.vk.VkOauthService;

@RequiredArgsConstructor
@RestController
public class OauthController implements OauthApi<AccountUserDetailsImpl> {

    private final VkOauthService vkOauthService;

    @Override
    public String getLoginLink() {
        return vkOauthService.getLoginLink();
    }

    @Override
    public AccessRefreshTokenPairResponse loginWithOauth(@AuthenticationPrincipal AccountUserDetailsImpl user,
                                                         String code) {
        return vkOauthService.loginWithOauth(user.getAccount().getId(), code);
    }
}

