package ru.asanvlit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import ru.asanvlit.dto.enums.ArtworkType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static ru.asanvlit.constant.YartoneApiConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request to create new post")
public class PostCreationRequest {

    @NotNull
    @Schema(description = "Artwork type", example = "PHOTOSHOP")
    private ArtworkType artworkType;

    @Range(min = POST_HOURS_MIN_SPENT, max = POST_HOURS_MAX_SPENT, message = "{postCreation.validation.hoursSpent}")
    @Schema(description = "Hours spent on artwork", example = "5")
    private Integer hoursSpent;

    @Length(max = POST_MATERIALS_MAX_LENGTH)
    @Schema(description = "Materials used for artwork")
    private String materials;

    @Length(max = POST_DESCRIPTION_MAX_LENGTH)
    @Schema(description = "Post description")
    private String description;

    @NotNull
    @Size(min = POST_ATTACHMENTS_MIN, max = POST_ATTACHMENTS_MAX)
    @Schema(description = "Set with file attachment ids")
    private Set<FileRequest> attachments;
}
