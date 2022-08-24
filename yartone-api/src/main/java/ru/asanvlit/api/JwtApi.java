package ru.asanvlit.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.asanvlit.dto.request.TokenPairRequest;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.dto.response.ExceptionResponse;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.asanvlit.constant.YartoneApiConstants.API;

@RequestMapping(API + "/token")
public interface JwtApi {

    @Operation(summary = "Refreshes tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccessRefreshTokenPairResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Refresh token not found/ Refresh token expired/ Invalid token",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/refresh", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    AccessRefreshTokenPairResponse refreshTokens(@RequestBody @Valid TokenPairRequest tokenPairRequest);

    @Operation(summary = "Get user-info by access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "401", description = "The token has expired/ Invalid token",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @GetMapping(value = "/user-info", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    AccountResponse userInfoByToken(@RequestParam String token);
}

