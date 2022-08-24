package ru.asanvlit.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.asanvlit.dto.request.AccountChangePasswordRequest;
import ru.asanvlit.dto.request.AccountUpdateRequest;
import ru.asanvlit.dto.request.FileRequest;
import ru.asanvlit.dto.response.AccountExtendedResponse;
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.dto.response.ExceptionResponse;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static ru.asanvlit.constant.YartoneApiConstants.API;

@RequestMapping(API + "/account")
public interface AccountApi<PRINCIPAL> {

    @Operation(summary = "Get account's extended info by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountExtendedResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @GetMapping(value = "/{account-id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    AccountExtendedResponse getAccountExtendedInfo(@PathVariable("account-id") UUID accountId);

    @Operation(summary = "Get accounts main-info page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
                            )
                    })
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Page<AccountResponse> getAccountsMainInfoPage(Pageable pageable);

    @Operation(summary = "Updates account's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Password updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Wrong previous password",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @PutMapping(value = "/password", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    AccountResponse updateAccountsPassword(@Parameter(hidden = true) PRINCIPAL user,
                                           @RequestBody @Valid AccountChangePasswordRequest changePasswordRequest);

    @Operation(summary = "Updates account's main info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Account's main info updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountExtendedResponse.class)
                            )
                    }
            )
    })
    @PatchMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    AccountExtendedResponse updateAccountsMainInfo(@Parameter(hidden = true) PRINCIPAL user,
                                                   @RequestBody @Valid AccountUpdateRequest accountUpdateRequest);

    @Operation(summary = "Updates account's main info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Account's profile photo updated successfully",
                    content = {
                            @Content(schema = @Schema(implementation = AccountExtendedResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "The specified attachment has invalid file type",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            )
    })
    @PutMapping(value = "/profile-photo", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    AccountExtendedResponse updateAccountsProfilePhoto(@Parameter(hidden = true) PRINCIPAL user,
                                                       @RequestBody @Valid FileRequest fileRequest);
}
