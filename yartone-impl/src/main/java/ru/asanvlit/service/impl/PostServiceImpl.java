package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.FileType;
import ru.asanvlit.dto.enums.PostState;
import ru.asanvlit.dto.request.PostCreationRequest;
import ru.asanvlit.dto.request.PostRequest;
import ru.asanvlit.dto.request.PostUpdateRequest;
import ru.asanvlit.dto.response.PostResponse;
import ru.asanvlit.exception.base.YartoneAccessDeniedException;
import ru.asanvlit.exception.illegalargument.YartoneInvalidFileTypeException;
import ru.asanvlit.exception.illegalstate.YartoneIllegalPostStateException;
import ru.asanvlit.exception.notfound.YartonePostNotFoundException;
import ru.asanvlit.model.FileInfoEntity;
import ru.asanvlit.model.PostEntity;
import ru.asanvlit.model.mongo.SubscriptionEntity;
import ru.asanvlit.repository.postgres.PostRepository;
import ru.asanvlit.service.AccountService;
import ru.asanvlit.service.FileService;
import ru.asanvlit.service.PostService;
import ru.asanvlit.service.SubscriptionService;
import ru.asanvlit.util.mapper.PostMapper;
import ru.asanvlit.util.string.StringUtil;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final AccountService accountService;

    private final SubscriptionService subscriptionService;

    private final FileService fileService;

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Override
    public PostResponse getPostResponseById(UUID postId) {
        return postMapper.toResponse(getPostById(postId));
    }

    @Override
    public UUID createPostDraft(UUID accountId, PostCreationRequest postCreationRequest) {
        PostEntity post = postMapper.toEntity(postCreationRequest);
        post.setState(PostState.DRAFT);
        post.setAuthor(accountService.getAccountById(accountId));
        post.setCreateDate(Instant.now());
        removeProhibitedTagsFromPost(post);

        Set<FileInfoEntity> attachments = postCreationRequest.getAttachments().stream()
                .map(f -> fileService.getFileInfoById(f.getId()))
                .map(f -> {
                    if (!FileType.POST_ATTACHMENT.equals(f.getFileType())) {
                        throw new YartoneInvalidFileTypeException(
                                String.format("File with id [%s] has not acceptable file type", f.getId())
                        );
                    }
                    return f;
                })
                .collect(Collectors.toSet());

        post.setAttachments(attachments);

        return postRepository.save(post).getId();
    }

    public PostEntity getPostById(UUID postId) {
        return postRepository.findById(postId).orElseThrow(YartonePostNotFoundException::new);
    }

    @Override
    public void publishPostDraft(UUID accountId, PostRequest postRequest) {
        PostEntity post = getPostById(postRequest.getId());

        if (!post.getAuthor().equals(accountService.getAccountById(accountId))) {
            throw new YartoneAccessDeniedException("Access denied for post publication");
        }

        if (PostState.DRAFT.equals(post.getState())) {
            post.setState(PostState.ACTIVE);
            post.setPublishedAt(Instant.now());
            postRepository.save(post);
        } else {
            throw new YartoneIllegalPostStateException(String.format("Unable to publish post with state [%s]", post.getState()));
        }
    }

    @Override
    public Boolean existsPostById(UUID postId) {
        return postRepository.existsById(postId);
    }

    @Override
    public PostResponse updatePost(UUID accountId, UUID postId, PostUpdateRequest postUpdateRequest) {
        return postRepository.findById(postId)
                .map(p -> {
                    if (!accountService.getAccountById(accountId).equals(p.getAuthor())) {
                        throw new YartoneAccessDeniedException("Access denied for post update");
                    }
                    if (!(PostState.ACTIVE.equals(p.getState()) || PostState.DRAFT.equals(p.getState()))) {
                        throw new YartoneIllegalPostStateException("Illegal post's state");
                    }
                    return p;
                })
                .map(p -> {postMapper.updatePost(p, postUpdateRequest); return p;})
                .map(p -> {removeProhibitedTagsFromPost(p); return p;})
                .map(postRepository::save)
                .map(postMapper::toResponse)
                .orElseThrow(YartonePostNotFoundException::new);
    }

    @Override
    public Page<PostResponse> getPostsFromFollowingAccounts(UUID accountId, Pageable pageable) {
        return postRepository.findAllByAuthor_IdInAndState(subscriptionService.getAccountsSubscriptions(accountId).stream()
                .map(SubscriptionEntity::getFollowsId)
                .collect(Collectors.toSet()), PostState.ACTIVE, pageable
                )
                .map(postMapper::toResponse);
    }

    private void removeProhibitedTagsFromPost(PostEntity post) {
        if (Objects.nonNull(post.getMaterials())) {
            post.setMaterials(StringUtil.removeProhibitedTags(post.getMaterials()));
        }
        if (Objects.nonNull(post.getDescription())) {
            post.setDescription(StringUtil.removeProhibitedTags(post.getDescription()));
        }
    }
}
