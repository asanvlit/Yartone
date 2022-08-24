package ru.asanvlit.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asanvlit.dto.enums.ConfirmCodeType;
import ru.asanvlit.model.ConfirmCodeTypeEntity;

import java.util.Optional;
import java.util.UUID;

public interface ConfirmCodeTypeRepository extends JpaRepository<ConfirmCodeTypeEntity, UUID> {

    Optional<ConfirmCodeTypeEntity> findByConfirmCodeType(ConfirmCodeType confirmCodeType);
}
