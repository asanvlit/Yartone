package ru.asanvlit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.asanvlit.dto.enums.FileType;

import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with file's info")
public class FileInfoResponse {

    @Schema(description = "File info id", example = "9e698189-ac27-4897-805f-be9d42499f2b")
    private UUID id;

    @Schema(description = "File's content type", example = "image/png")
    private String contentType;

    @Schema(description = "File's type", example = "PROFILE_PHOTO")
    private FileType fileType;

    @Schema(description = "File's original name", example = "cat.png")
    private String originalName;

    @Schema(description = "File's storage name", example = "62cc6c779e472a0a8b8110db")
    private String storageName;

    @Schema(description = "File's size", example = "465068")
    private Long size;

    @Schema(description = "File's description")
    private String description;
}

