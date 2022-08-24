package ru.asanvlit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import ru.asanvlit.api.InteractionApi;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.dto.response.LikeResponse;
import ru.asanvlit.security.details.AccountUserDetailsImpl;
import ru.asanvlit.service.InteractionService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class InteractionController implements InteractionApi<AccountUserDetailsImpl> {

    private final InteractionService interactionService;

    @Override
    public UUID likePost(@AuthenticationPrincipal AccountUserDetailsImpl account, UUID postId) {
        return interactionService.likePost(account.getAccount().getId(), postId);
    }

    @Override
    public void deleteLikeFromPost(@AuthenticationPrincipal AccountUserDetailsImpl account, UUID postId) {
        interactionService.deleteLikeFromPost(account.getAccount().getId(), postId);
    }

    @Override
    public Page<LikeResponse> getPostsLikes(UUID postId, Pageable pageable) {
        return interactionService.getPostsLikesPage(postId, pageable);
    }

    @Override
    public UUID followAccount(@AuthenticationPrincipal AccountUserDetailsImpl account, UUID accountId) {
        return interactionService.followAccount(account.getAccount().getId(), accountId);
    }

    @Override
    public void unfollowAccount(@AuthenticationPrincipal AccountUserDetailsImpl account, UUID accountId) {
        interactionService.unfollowAccount(account.getAccount().getId(), accountId);
    }

    @Override
    public Page<AccountResponse> getAccountsFollowers(UUID accountId, Pageable pageable) {
        return interactionService.getAccountsFollowersPage(accountId, pageable);
    }

    @Override
    public Page<AccountResponse> getAccountsSubscriptions(UUID accountId, Pageable pageable) {
        return interactionService.getAccountsSubscriptionsPage(accountId, pageable);
    }
}
