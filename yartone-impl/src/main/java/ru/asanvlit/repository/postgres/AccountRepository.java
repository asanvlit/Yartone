package ru.asanvlit.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.asanvlit.model.AccountEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query(nativeQuery = true,
            value = "SELECT account.id, email, role, state, account.create_date, username, name, biography, birthdate, password_hash" +
                    "  FROM confirm_code INNER JOIN" +
                    "       account ON account_id = account.id INNER JOIN" +
                    "       confirm_code_type cct ON confirm_code.code_type_id = cct.id" +
                    "  WHERE code_type = 'ACCOUNT_CONFIRM'" +
                    "  AND CURRENT_TIMESTAMP - confirm_code.create_date >= CAST(concat(valid_time, ' hours') AS interval);")
    Set<AccountEntity> getExpiredUnconfirmedAccounts();
}
