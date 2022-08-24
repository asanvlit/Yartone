package ru.asanvlit.repository.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.asanvlit.model.mongo.LikeEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface LikeRepository extends MongoRepository<LikeEntity, UUID> {

    Set<LikeEntity> findByPostId(UUID postId);

    Page<LikeEntity> findByPostId(UUID postId, Pageable pageable);

    Integer countByPostId(UUID postId);

    boolean existsByAccountIdAndPostId(UUID accountId, UUID postId);

    Optional<LikeEntity> findByAccountIdAndPostId(UUID accountId, UUID postId);
}
