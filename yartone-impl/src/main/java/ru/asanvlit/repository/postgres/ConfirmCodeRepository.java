package ru.asanvlit.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.asanvlit.dto.enums.ConfirmCodeType;
import ru.asanvlit.model.ConfirmCodeEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ConfirmCodeRepository extends JpaRepository<ConfirmCodeEntity, UUID> {

    Optional<ConfirmCodeEntity> findByAccount_IdAndConfirmCodeType_ConfirmCodeType(UUID accountId, ConfirmCodeType codeType);

    Optional<ConfirmCodeEntity> findByCodeAndConfirmCodeType_ConfirmCodeType(String codeValue, ConfirmCodeType codeType);

    @Query(nativeQuery = true,
            value = "SELECT confirm_code.id, confirm_code.create_date, account_id, code, code_type_id" +
                    "  FROM confirm_code INNER JOIN" +
                    "   confirm_code_type cct ON confirm_code.code_type_id = cct.id" +
                    "  AND CURRENT_TIMESTAMP - confirm_code.create_date >= CAST(concat(valid_time, ' hours') AS interval);")
    Set<ConfirmCodeEntity> getExpiredConfirmCodes();
}
