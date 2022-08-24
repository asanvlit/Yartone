package ru.asanvlit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with post's likes")
public class LikeResponse {

    @Schema(description = "Like's id", example = "9e698189-ac27-4897-805f-be9d42499f2b")
    private UUID id;

    @Schema(description = "Like's create date", example = "9e698189-ac27-4897-805f-be9d42499f2b")
    private Instant createDate;

    @Schema(description = "The account that put the like")
    private AccountResponse account;
}

