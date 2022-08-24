package ru.asanvlit.security.oauth.vk.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.asanvlit.exception.illegalstate.YartoneOauthIllegalStateException;
import ru.asanvlit.exception.notfound.YartoneAccountNotFoundException;
import ru.asanvlit.model.VkOauthAccountAccessTokenEntity;
import ru.asanvlit.repository.postgres.VkOauthAccountAccessTokenRepository;
import ru.asanvlit.security.oauth.vk.VkOauthTokenService;
import ru.asanvlit.service.AccountService;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VkOauthTokenServiceImpl implements VkOauthTokenService {

    private final AccountService accountService;

    private final VkOauthAccountAccessTokenRepository vkOauthAccountAccessTokenRepository;

    @Override
    public UUID saveAccountAccessToken(VkOauthAccountAccessTokenEntity vkOauthAccountAccessToken) {
        if (vkOauthAccountAccessTokenRepository.existsByVkAccountId(vkOauthAccountAccessToken.getVkAccountId())) {
            throw new YartoneOauthIllegalStateException("Already have an account linked to this VK account");
        }
        return vkOauthAccountAccessTokenRepository.save(vkOauthAccountAccessToken).getId();
    }

    @Override
    public Optional<VkOauthAccountAccessTokenEntity> getAccountAccessTokenByAccountId(UUID accountId) {
        if (!accountService.existsAccountById(accountId)) {
            throw new YartoneAccountNotFoundException();
        }
        return vkOauthAccountAccessTokenRepository.findByAccount_Id(accountId);
    }

    @Override
    public Boolean existsVkAccountAccessTokenByAccountId(UUID accountId) {
        if (!accountService.existsAccountById(accountId)) {
            throw new YartoneAccountNotFoundException();
        }
        return vkOauthAccountAccessTokenRepository.existsByAccount_Id(accountId);
    }
}
