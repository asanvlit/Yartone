package ru.asanvlit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.asanvlit.dto.response.LikeResponse;
import ru.asanvlit.model.mongo.LikeEntity;

import java.util.Set;
import java.util.UUID;

public interface LikeService {

    Set<LikeEntity> getPostsLikes(UUID postId);

    Integer getPostsLikesCount(UUID postId);

    UUID likePost(UUID accountId, UUID postId);

    void deleteLikeFromPost(UUID accountId, UUID postId);

    Page<LikeResponse> getPostsLikesPage(UUID postId, Pageable pageable);
}
