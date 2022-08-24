package ru.asanvlit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.asanvlit.dto.enums.FileType;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file_info")
public class FileInfoEntity extends AbstractEntity {

    @Column(name = "content_type", nullable = false, columnDefinition = "VARCHAR(50)")
    private String contentType;

    @Column(name = "file_type", columnDefinition = "VARCHAR(50)")
    @Enumerated(value = EnumType.STRING)
    private FileType fileType;

    @Column(name = "original_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String originalName;

    @Column(name = "storage_name", nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private String storageName;

    @Column(nullable = false)
    private Long size;

    @Column(columnDefinition = "VARCHAR(150)")
    private String description;
}

