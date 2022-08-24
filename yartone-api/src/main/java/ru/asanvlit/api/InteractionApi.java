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
import ru.asanvlit.dto.response.AccountResponse;
import ru.asanvlit.dto.response.ExceptionResponse;
import ru.asanvlit.dto.response.LikeResponse;
import ru.asanvlit.dto.response.PostResponse;

import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static ru.asanvlit.constant.YartoneApiConstants.API;

@RequestMapping(API)
public interface InteractionApi<PRINCIPAL> {

    @Operation(summary = "Likes the post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Like saved successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UUID.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Like from this account on this post already exists/" +
                    " Invalid post's state",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @PostMapping(value = "/post/{post-id}/like", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID likePost(@Parameter(hidden = true) PRINCIPAL account,
                  @PathVariable("post-id") UUID postId);

    @Operation(summary = "Likes the post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Like deleted successfully"),
            @ApiResponse(responseCode = "400", description = "There is no like from this account on this post/" +
                    " Invalid post's state",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @DeleteMapping(value = "/post/{post-id}/like", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void deleteLikeFromPost(@Parameter(hidden = true) PRINCIPAL account,
                            @PathVariable("post-id") UUID postId);

    @Operation(summary = "Get post's info by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @GetMapping(value = "/post/{post-id}/like", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Page<LikeResponse> getPostsLikes(@PathVariable("post-id") UUID postId,
                                     Pageable pageable);

    @Operation(summary = "Creates an account subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A subscription created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UUID.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Already existing subscription/" +
                    " Invalid account's state",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @PostMapping(value = "/account/{account-id}/follower", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID followAccount(@Parameter(hidden = true) PRINCIPAL account,
                       @PathVariable("account-id") UUID accountId);

    @Operation(summary = "Removes an account subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A subscription deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UUID.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Subscription doesn't exist/" +
                    " Invalid account's state",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @DeleteMapping(value = "/account/{account-id}/follower", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void unfollowAccount(@Parameter(hidden = true) PRINCIPAL account,
                         @PathVariable("account-id") UUID accountId);

    @Operation(summary = "Get account's followers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @GetMapping(value = "/account/{account-id}/follower", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Page<AccountResponse> getAccountsFollowers(@PathVariable("account-id") UUID accountId,
                                               Pageable pageable);

    @Operation(summary = "Get account's subscriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @GetMapping(value = "/account/{account-id}/subscription", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Page<AccountResponse> getAccountsSubscriptions(@PathVariable("account-id") UUID accountId,
                                                   Pageable pageable);
}
