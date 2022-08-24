package ru.asanvlit.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asanvlit.model.VkOauthAccountAccessTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface VkOauthAccountAccessTokenRepository extends JpaRepository<VkOauthAccountAccessTokenEntity, UUID> {

    boolean existsByAccount_Id(UUID accountId);

    boolean existsByVkAccountId(Long vkAccountId);

    Optional<VkOauthAccountAccessTokenEntity> findByAccount_Id(UUID accountId);
}

