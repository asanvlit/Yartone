package ru.asanvlit.util.mapper;

import lombok.NonNull;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Component
public class PersistenceBasicMapper {

    @PersistenceContext
    private EntityManager entityManager;

    @ObjectFactory
    public <T> T map(@NonNull final UUID id, @TargetType Class<T> type) {
        return entityManager.getReference(type, id);
    }
}

