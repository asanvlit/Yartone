package ru.asanvlit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.model.mongo.SubscriptionEntity;

import java.util.Set;
import java.util.UUID;

public interface SubscriptionService {

    UUID followAccount(UUID followerId, UUID followsId);

    void unfollowAccount(UUID followerId, UUID followsId);

    Page<AccountResponse> getAccountsFollowersPage(UUID accountId, Pageable pageable);

    Page<AccountResponse> getAccountsSubscriptionsPage(UUID accountId, Pageable pageable);

    Set<SubscriptionEntity> getAccountsSubscriptions(UUID accountId);
}
