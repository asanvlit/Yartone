package ru.asanvlit.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.asanvlit.dto.response.AccessRefreshTokenPairResponse;
import ru.asanvlit.dto.response.ExceptionResponse;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static ru.asanvlit.constant.YartoneApiConstants.API;

@RequestMapping(API + "/oauth")
public interface OauthApi<PRINCIPAL> {

    @Operation(summary = "Get link for vk authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful",
                    content = {@Content(schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/vk/link/login")
    @ResponseStatus(HttpStatus.OK)
    String getLoginLink();

    @Operation(summary = "Get access & refresh tokens pair by vk oauth code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login via VK",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccessRefreshTokenPairResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "405", description = "Problems with web client (connection lost, already used/ illegal code etc.)",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @GetMapping(value = "/vk/access/account", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    AccessRefreshTokenPairResponse loginWithOauth(@Parameter(hidden = true) PRINCIPAL user,
                                                  @Param("code") String code);
}

