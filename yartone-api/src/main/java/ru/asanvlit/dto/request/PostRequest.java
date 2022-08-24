package ru.asanvlit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request with post's id")
public class PostRequest {

    @NotNull
    @Schema(description = "Post's id", example = "9e698189-ac27-4897-805f-be9d42499f2b")
    private UUID id;
}
