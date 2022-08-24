package ru.asanvlit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import ru.asanvlit.api.PostApi;
import ru.asanvlit.dto.request.PostCreationRequest;
import ru.asanvlit.dto.request.PostRequest;
import ru.asanvlit.dto.request.PostUpdateRequest;
import ru.asanvlit.dto.response.PostResponse;
import ru.asanvlit.security.details.AccountUserDetailsImpl;
import ru.asanvlit.service.PostService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PostController implements PostApi<AccountUserDetailsImpl> {

    private final PostService postService;

    @Override
    public PostResponse getPost(UUID postId) {
        return postService.getPostResponseById(postId);
    }

    @Override
    public UUID createPostDraft(@AuthenticationPrincipal AccountUserDetailsImpl account,
                                PostCreationRequest postCreationRequest) {
        return postService.createPostDraft(account.getAccount().getId(), postCreationRequest);
    }

    @Override
    public void publishPostDraft(@AuthenticationPrincipal AccountUserDetailsImpl account,
                                 PostRequest postRequest) {
        postService.publishPostDraft(account.getAccount().getId(), postRequest);
    }

    @Override
    public PostResponse updatePost(@AuthenticationPrincipal AccountUserDetailsImpl account,
                                   UUID postId,
                                   PostUpdateRequest postUpdateRequest) {
        return postService.updatePost(account.getAccount().getId(), postId, postUpdateRequest);
    }

    @Override
    public Page<PostResponse> getPostsFromFollowingAccounts(@AuthenticationPrincipal AccountUserDetailsImpl account,
                                                            Pageable pageable) {
        return postService.getPostsFromFollowingAccounts(account.getAccount().getId(), pageable);
    }
}

