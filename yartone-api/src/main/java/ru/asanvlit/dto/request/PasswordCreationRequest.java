package ru.asanvlit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.asanvlit.validation.annotation.EqualPasswords;
import ru.asanvlit.validation.annotation.IsValidPassword;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualPasswords(password = "password", passwordRepeat = "passwordRepeat")
@Schema(description = "Request to set user's password")
public class PasswordCreationRequest {

    @IsValidPassword
    @NotBlank
    @Schema(description = "New potential password", example = "h78bGjndks!nds")
    private String password;

    @NotBlank
    @Schema(description = "New repeated potential password", example = "h78bGjndks!nds")
    private String passwordRepeat;
}

