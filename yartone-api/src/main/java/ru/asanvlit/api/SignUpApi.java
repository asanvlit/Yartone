package ru.asanvlit.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.asanvlit.dto.request.SignUpRequest;
import ru.asanvlit.validation.response.ValidationExceptionResponse;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static ru.asanvlit.constant.YartoneApiConstants.API;

@RequestMapping(API)
public interface SignUpApi {

    @Operation(summary = "Creates account by email and username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = {
                        @Content(schema = @Schema(implementation = UUID.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Sign-up form validation failed",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationExceptionResponse.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/sign-up", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID signUp(@RequestBody @Valid SignUpRequest signUpRequest);
}

