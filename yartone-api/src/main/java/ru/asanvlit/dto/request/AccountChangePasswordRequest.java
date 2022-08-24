package ru.asanvlit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.asanvlit.validation.annotation.EqualPasswords;
import ru.asanvlit.validation.annotation.IsValidPassword;

import javax.validation.constraints.NotBlank;

@EqualPasswords(password = "password", passwordRepeat = "passwordRepeat")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to update the account's password")
public class AccountChangePasswordRequest {

    @NotBlank
    @Schema(description = "Previous password", example = "kn628jvH!jkc")
    private String previousPassword;

    @IsValidPassword
    @NotBlank
    @Schema(description = "New potential password", example = "h78bGjndks!nds")
    private String password;

    @NotBlank
    @Schema(description = "Repeated new potential password", example = "h78bGjndks!nds")
    private String passwordRepeat;
}
