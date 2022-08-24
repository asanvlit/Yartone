package ru.asanvlit.validation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Schema(description = "Response with validation error info")
public class ValidationErrorResponse {

    @Schema(description = "The name of the object that was validated")
    private String object;

    @Schema(description = "The error message text for this rejected field")
    private String rejectedFieldErrorMessage;

    @Schema(description = "The name of the object field that was rejected")
    private String rejectedField;
}
