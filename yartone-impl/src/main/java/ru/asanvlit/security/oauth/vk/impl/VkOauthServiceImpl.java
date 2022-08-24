package ru.asanvlit.security.oauth.vk.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.AccountRole;
import ru.asanvlit.dto.enums.AccountState;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.dto.response.vk.VkAccountAccessTokenResponse;
import ru.asanvlit.dto.response.vk.VkAccountResponse;
import ru.asanvlit.exception.illegalargument.YartoneOauthUserDataException;
import ru.asanvlit.model.AccountEntity;
import ru.asanvlit.model.VkOauthAccountAccessTokenEntity;
import ru.asanvlit.security.jwt.JwtService;
import ru.asanvlit.security.oauth.vk.VkOauthService;
import ru.asanvlit.security.oauth.vk.VkOauthTokenService;
import ru.asanvlit.security.oauth.vk.VkOauthWebClient;
import ru.asanvlit.service.AccountService;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class VkOauthServiceImpl implements VkOauthService {

    private final AccountService accountService;

    private final VkOauthTokenService vkOauthTokenService;

    private final JwtService jwtService;

    private final VkOauthWebClient vkOauthWebClient;

    @Override
    public String getLoginLink() {
        return vkOauthWebClient.generateInvitationLink();
    }

    @Override
    public AccessRefreshTokenPairResponse loginWithOauth(UUID accountId, String code) {
        return jwtService.getAccessRefreshTokenPair(accountId);
    }

    @Override
    public Boolean isVkAuthorizedAccount(UUID accountId) {
        return vkOauthTokenService.existsVkAccountAccessTokenByAccountId(accountId);
    }

    @Override
    public AccountEntity getVkAccount(String code) {
        VkAccountAccessTokenResponse token = vkOauthWebClient.getOauthToken(code);

        if (Objects.nonNull(token.getEmail())) {

            if (!accountService.existsAccountByEmail(token.getEmail())) {
                VkAccountResponse vkAccountResponse = vkOauthWebClient.getVkAccountInfo(token.getUserId(), token.getAccessToken());

                AccountEntity newAccount = accountService.saveAccount(AccountEntity.builder()
                        .email(token.getEmail())
                        .username(accountService.generateUsername(vkAccountResponse.getFirstName(),
                                vkAccountResponse.getLastName()))
                        .state(AccountState.ACTIVE)
                        .role(AccountRole.USER)
                        .createDate(Instant.now())
                        .build()
                );

                vkOauthTokenService.saveAccountAccessToken(VkOauthAccountAccessTokenEntity.builder()
                        .account(accountService.getAccountById(newAccount.getId()))
                        .vkAccountId(token.getUserId())
                        .accessToken(token.getAccessToken())
                        .build());

                return newAccount;
            } else {
                return accountService.getAccountByEmail(token.getEmail());
            }
        } else {
            log.info(String.format("Failed to authorize a user [%s] with not specified email", token.getUserId()));
            throw new YartoneOauthUserDataException("Could not authorize a user who doesn't have an email specified");
        }
    }
}

