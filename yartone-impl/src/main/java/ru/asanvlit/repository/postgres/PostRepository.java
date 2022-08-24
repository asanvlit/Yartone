package ru.asanvlit.repository.postgres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.asanvlit.dto.enums.PostState;
import ru.asanvlit.model.PostEntity;

import java.util.Set;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    Page<PostEntity> findAllByAuthor_IdInAndState(Set<UUID> authorIds, PostState postState, Pageable pageable);
}
