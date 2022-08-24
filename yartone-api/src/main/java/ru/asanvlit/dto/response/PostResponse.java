package ru.asanvlit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.asanvlit.dto.enums.ArtworkType;
import ru.asanvlit.dto.enums.PostState;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with post info")
public class PostResponse {

    @Schema(description = "Post's id", example = "9e698189-ac27-4897-805f-be9d42499f2b")
    private UUID id;

    @Schema(description = "Artwork type", example = "PHOTOSHOP")
    private ArtworkType artworkType;

    @Schema(description = "State", example = "ACTIVE")
    private PostState state;

    @Schema(description = "Hours spent on artwork", example = "5")
    private Integer hoursSpent;

    @Schema(description = "Materials used for artwork")
    private String materials;

    @Schema(description = "Post description")
    private String description;

    @Schema(description = "Publication date")
    private Instant publishedAt;

    @Schema(description = "Likes count", example = "100")
    private Integer likesCount;

    @Schema(description = "Author")
    private AccountResponse author;

    @Schema(description = "Set with file attachment infos")
    private Set<FileInfoResponse> attachments;
}
