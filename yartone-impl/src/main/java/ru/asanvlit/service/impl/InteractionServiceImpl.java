package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.dto.response.LikeResponse;
import ru.asanvlit.service.InteractionService;
import ru.asanvlit.service.LikeService;
import ru.asanvlit.service.SubscriptionService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InteractionServiceImpl implements InteractionService {

    private final LikeService likeService;

    private final SubscriptionService subscriptionService;

    @Override
    public UUID likePost(UUID accountId, UUID postId) {
        return likeService.likePost(accountId, postId);
    }

    @Override
    public void deleteLikeFromPost(UUID accountId, UUID postId) {
        likeService.deleteLikeFromPost(accountId, postId);
    }

    @Override
    public Page<LikeResponse> getPostsLikesPage(UUID postId, Pageable pageable) {
        return likeService.getPostsLikesPage(postId, pageable);
    }

    @Override
    public UUID followAccount(UUID followerId, UUID followsId) {
        return subscriptionService.followAccount(followerId, followsId);
    }

    @Override
    public void unfollowAccount(UUID followerId, UUID followsId) {
        subscriptionService.unfollowAccount(followerId, followsId);
    }

    @Override
    public Page<AccountResponse> getAccountsFollowersPage(UUID accountId, Pageable pageable) {
        return subscriptionService.getAccountsFollowersPage(accountId, pageable);
    }

    @Override
    public Page<AccountResponse> getAccountsSubscriptionsPage(UUID accountId, Pageable pageable) {
        return subscriptionService.getAccountsSubscriptionsPage(accountId, pageable);
    }
}
