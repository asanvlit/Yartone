package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.PostState;
import ru.asanvlit.dto.response.LikeResponse;
import ru.asanvlit.exception.illegalstate.YartoneIllegalLikeStateException;
import ru.asanvlit.exception.illegalstate.YartoneIllegalPostStateException;
import ru.asanvlit.exception.notfound.YartonePostNotFoundException;
import ru.asanvlit.model.mongo.LikeEntity;
import ru.asanvlit.repository.mongo.LikeRepository;
import ru.asanvlit.service.LikeService;
import ru.asanvlit.service.PostService;
import ru.asanvlit.util.mapper.LikeMapper;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final PostService postService;

    private final LikeRepository likeRepository;

    private final LikeMapper likeMapper;

    @Override
    public Set<LikeEntity> getPostsLikes(UUID postId) {
        return likeRepository.findByPostId(postId);
    }

    @Override
    public Integer getPostsLikesCount(UUID postId) {
        return likeRepository.countByPostId(postId);
    }

    @Override
    public UUID likePost(UUID accountId, UUID postId) {
        if (!PostState.ACTIVE.equals(postService.getPostById(postId).getState())) {
            throw new YartoneIllegalPostStateException();
        }
        if (likeRepository.existsByAccountIdAndPostId(accountId, postId)) {
            throw new YartoneIllegalLikeStateException("Like from this account on this post already exists");
        }

        return likeRepository.save(LikeEntity.builder()
                .createDate(Instant.now())
                .accountId(accountId)
                .postId(postId)
                .build()).getId();
    }

    @Override
    public void deleteLikeFromPost(UUID accountId, UUID postId) {
        if (!PostState.ACTIVE.equals(postService.getPostById(postId).getState())) {
            throw new YartoneIllegalPostStateException();
        }

        Optional<LikeEntity> like = likeRepository.findByAccountIdAndPostId(accountId, postId);
        if (like.isEmpty()) {
            throw new YartoneIllegalLikeStateException("There is no like from this account on this post");
        } else {
            likeRepository.delete(like.get());
        }
    }

    @Override
    public Page<LikeResponse> getPostsLikesPage(UUID postId, Pageable pageable) {
        if (!postService.existsPostById(postId)) {
            throw new YartonePostNotFoundException();
        }

        return likeRepository.findByPostId(postId, pageable)
                .map(likeMapper::toResponse);
    }
}

