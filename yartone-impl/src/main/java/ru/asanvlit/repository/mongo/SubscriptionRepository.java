package ru.asanvlit.repository.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.asanvlit.model.mongo.SubscriptionEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SubscriptionRepository extends MongoRepository<SubscriptionEntity, UUID> {

    boolean existsByFollowerIdAndFollowsId(UUID followerId, UUID followsId);

    Page<SubscriptionEntity> findByFollowsId(UUID followsId, Pageable pageable);

    Page<SubscriptionEntity> findByFollowerId(UUID followsId, Pageable pageable);

    Set<SubscriptionEntity> findByFollowerId(UUID followsId);

    Optional<SubscriptionEntity> findByFollowerIdAndFollowsId(UUID followerId, UUID followsId);
}
