package ru.asanvlit.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.asanvlit.dto.request.EmailRequest;
import ru.asanvlit.dto.request.PasswordCreationRequest;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.dto.response.ExceptionResponse;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static ru.asanvlit.constant.YartoneApiConstants.API;

@RequestMapping(API)
public interface ConfirmApi {

    @Operation(summary = "Confirms the account using the confirmation code from the email and sets a password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully confirmed account",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccessRefreshTokenPairResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Not existing confirm code/ Confirm code expired",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/account/confirmation/{confirm-code-value}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    AccessRefreshTokenPairResponse confirmAccount(@PathVariable("confirm-code-value") UUID confirmCode,
                                                  @RequestBody @Valid PasswordCreationRequest passwordCreationRequest);

    @Operation(summary = "Resends the account confirmation code to the email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully resent account confirmation email"),
            @ApiResponse(responseCode = "404", description = "The account with this email was not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "The time for account confirmation has expired/ " +
                    "The account has already been confirmed",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/account/confirmation/confirm-code", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void resendEmailAccountConfirmCode(@RequestBody @Valid EmailRequest emailRequest);

    @Operation(summary = "Sends an email to the specified account to reset the password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully sent an email with a link to reset the password"),
            @ApiResponse(responseCode = "404", description = "The account with this email was not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Illegal account state/ " +
                    "The reset-password email has already been sent",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/account/password-reset", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void resetPassword(@RequestBody @Valid EmailRequest emailRequest);

    @Operation(summary = "Creates a new password using the password-reset confirmation code sent to the email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully set a new password after reset",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccessRefreshTokenPairResponse.class
                            )
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Not existing confirm code/ Confirm code expired",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/account/password-reset/{confirm-code-value}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    AccessRefreshTokenPairResponse createPasswordAfterReset(@PathVariable("confirm-code-value") UUID confirmCode,
                                                            @RequestBody @Valid PasswordCreationRequest passwordCreationRequest);
}

