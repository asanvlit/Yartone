package ru.asanvlit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.dto.response.LikeResponse;

import java.util.UUID;

public interface InteractionService {

    UUID likePost(UUID accountId, UUID postId);

    void deleteLikeFromPost(UUID accountId, UUID postId);

    Page<LikeResponse> getPostsLikesPage(UUID postId, Pageable pageable);

    UUID followAccount(UUID followerId, UUID followsId);

    void unfollowAccount(UUID followerId, UUID followsId);

    Page<AccountResponse> getAccountsFollowersPage(UUID accountId, Pageable pageable);

    Page<AccountResponse> getAccountsSubscriptionsPage(UUID accountId, Pageable pageable);
}
