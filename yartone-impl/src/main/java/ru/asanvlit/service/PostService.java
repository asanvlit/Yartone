package ru.asanvlit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.asanvlit.dto.request.PostCreationRequest;
import ru.asanvlit.dto.request.PostRequest;
import ru.asanvlit.dto.request.PostUpdateRequest;
import ru.asanvlit.dto.response.PostResponse;
import ru.asanvlit.model.PostEntity;

import java.util.UUID;

public interface PostService {

    PostResponse getPostResponseById(UUID postId);

    PostEntity getPostById(UUID postId);

    UUID createPostDraft(UUID accountId, PostCreationRequest postCreationRequest);

    void publishPostDraft(UUID accountId, PostRequest postRequest);

    Boolean existsPostById(UUID postId);

    PostResponse updatePost(UUID accountId, UUID postId, PostUpdateRequest postUpdateRequest);

    Page<PostResponse> getPostsFromFollowingAccounts(UUID accountId, Pageable pageable);
}
