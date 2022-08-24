package ru.asanvlit.validation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.asanvlit.dto.response.ExceptionResponse;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Schema(description = "Response with validation errors")
public class ValidationExceptionResponse extends ExceptionResponse {

    @Schema(description = "List of validation errors info")
    private List<ValidationErrorResponse> errors;
}

