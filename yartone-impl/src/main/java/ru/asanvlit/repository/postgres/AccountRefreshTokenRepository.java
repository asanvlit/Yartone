package ru.asanvlit.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asanvlit.model.AccountRefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface AccountRefreshTokenRepository extends JpaRepository<AccountRefreshTokenEntity, UUID> {

    Optional<AccountRefreshTokenEntity> findByAccount_Id(UUID accountId);
}
