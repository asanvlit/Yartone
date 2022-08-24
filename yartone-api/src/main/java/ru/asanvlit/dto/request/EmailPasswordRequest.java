package ru.asanvlit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request with email + password")
public class EmailPasswordRequest {

    @Email
    @NotBlank
    @Schema(description = "Email", example = "test@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "Password", example = "h78bGjndks!nds")
    private String password;
}
