package ru.asanvlit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.asanvlit.dto.enums.AccountState;

import java.time.LocalDate;
import java.util.Set;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Extended account response")
public class AccountExtendedResponse extends AccountResponse {

    @Schema(description = "Biography")
    private String biography;

    @Schema(description = "Birthdate", example = "2022-07-12")
    private LocalDate birthdate;

    @Schema(description = "State", example = "ACTIVE")
    private AccountState state;

    @Schema(description = "Profile photo")
    private FileInfoResponse profilePhoto;

    @Schema(description = "Set of account's posts")
    private Set<PostResponse> posts;
}

