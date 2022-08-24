package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.AccountState;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.exception.illegalargument.YartoneIllegalSubscriptionException;
import ru.asanvlit.exception.illegalstate.YartoneIllegalAccountStateException;
import ru.asanvlit.exception.illegalstate.YartoneIllegalSubscriptionStateException;
import ru.asanvlit.exception.notfound.YartoneAccountNotFoundException;
import ru.asanvlit.model.mongo.SubscriptionEntity;
import ru.asanvlit.repository.mongo.SubscriptionRepository;
import ru.asanvlit.service.AccountService;
import ru.asanvlit.service.SubscriptionService;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final AccountService accountService;

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public UUID followAccount(UUID followerId, UUID followsId) {
        if (!AccountState.ACTIVE.equals(accountService.getAccountById(followsId).getState())) {
            throw new YartoneIllegalAccountStateException();
        }
        if (subscriptionRepository.existsByFollowerIdAndFollowsId(followerId, followsId)) {
            throw new YartoneIllegalSubscriptionStateException("Already existing subscription");
        }
        if (followerId.equals(followsId)) {
            throw new YartoneIllegalSubscriptionException("Account can't be followed by itself");
        }

        return subscriptionRepository.save(SubscriptionEntity.builder()
                .createDate(Instant.now())
                .followerId(followerId)
                .followsId(followsId)
                .build()).getId();
    }

    @Override
    public void unfollowAccount(UUID followerId, UUID followsId) {
        if (!AccountState.ACTIVE.equals(accountService.getAccountById(followsId).getState())) {
            throw new YartoneIllegalAccountStateException();
        }

        Optional<SubscriptionEntity> subscription = subscriptionRepository.findByFollowerIdAndFollowsId(followerId, followsId);
        if (subscription.isEmpty()) {
            throw new YartoneIllegalSubscriptionStateException("Subscription doesn't exist");
        } else {
            subscriptionRepository.delete(subscription.get());
        }
    }

    @Override
    public Page<AccountResponse> getAccountsFollowersPage(UUID accountId, Pageable pageable) {
        if (!accountService.existsAccountById(accountId)) {
            throw new YartoneAccountNotFoundException();
        }

        return subscriptionRepository.findByFollowsId(accountId, pageable)
                .map(s -> accountService.getAccountResponse(s.getFollowerId()));
    }

    @Override
    public Page<AccountResponse> getAccountsSubscriptionsPage(UUID accountId, Pageable pageable) {
        if (!accountService.existsAccountById(accountId)) {
            throw new YartoneAccountNotFoundException();
        }

        return subscriptionRepository.findByFollowerId(accountId, pageable)
                .map(s -> accountService.getAccountResponse(s.getFollowerId()));
    }

    @Override
    public Set<SubscriptionEntity> getAccountsSubscriptions(UUID accountId) {
        if (!accountService.existsAccountById(accountId)) {
            throw new YartoneAccountNotFoundException();
        }

        return subscriptionRepository.findByFollowerId(accountId);
    }
}

