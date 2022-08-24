package ru.asanvlit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import ru.asanvlit.dto.enums.ArtworkType;

import static ru.asanvlit.constant.YartoneApiConstants.*;
import static ru.asanvlit.constant.YartoneApiConstants.POST_DESCRIPTION_MAX_LENGTH;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to update post's main info")
public class PostUpdateRequest {

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
}
