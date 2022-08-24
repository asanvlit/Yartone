package ru.asanvlit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static ru.asanvlit.constant.YartoneApiConstants.USERNAME_PATTERN;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to register the user")
public class SignUpRequest {

    @Pattern(regexp = USERNAME_PATTERN, message = "{signUpRequest.validation.username}")
    @NotBlank
    @Schema(description = "Username", example = "test_user")
    private String username;

    @Email
    @NotBlank
    @Schema(description = "Email", example = "test@gmail.com")
    private String email;
}

