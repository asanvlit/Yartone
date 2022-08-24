package ru.asanvlit.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.asanvlit.model.FileInfoEntity;

import java.util.Set;
import java.util.UUID;

public interface FileInfoRepository extends JpaRepository<FileInfoEntity, UUID> {

    @Query("  SELECT CASE " +
            "   WHEN (f.fileType='POST_ATTACHMENT' AND (SELECT COUNT(p) FROM PostEntity p WHERE f.id IN (SELECT f.id FROM p.attachments)) = 0) THEN f.id" +
            "   WHEN (f.fileType='PROFILE_PHOTO' AND (SELECT COUNT(a) FROM AccountEntity a WHERE a.profilePhoto.id = f.id) = 0) THEN f.id" +
            "   ELSE NULL" +
            " END " +
            " FROM FileInfoEntity f")
    Set<UUID> getUnusedFilesIdSet();
}
