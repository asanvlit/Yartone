package ru.asanvlit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Schema(description = "Response with exception info")
public class ExceptionResponse {

    @Schema(description = "Description of the path along which the exceptional situation occurred")
    private String endpoint;

    @Schema(description = "Exception message", example = "Access token expired")
    private String message;

    @Schema(description = "Detailed Exception Message")
    private String detailMessage;

    @Schema(description = "Exception name", example = "AgonaEpJwtExpiredException")
    private String exceptionName;
}

