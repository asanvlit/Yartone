package ru.asanvlit.validation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Schema(description = "Extended response with field-validation error info")
public class ExtendedValidationErrorResponse extends ValidationErrorResponse {

    @Schema(description = "The value of the object field that was rejected")
    private String rejectedFieldValue;
}
