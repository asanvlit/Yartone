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
import ru.asanvlit.dto.request.PostCreationRequest;
import ru.asanvlit.dto.request.PostRequest;
import ru.asanvlit.dto.request.PostUpdateRequest;
import ru.asanvlit.dto.response.ExceptionResponse;
import ru.asanvlit.dto.response.PostResponse;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static ru.asanvlit.constant.YartoneApiConstants.API;

@RequestMapping(API)
public interface PostApi<PRINCIPAL> {

    @Operation(summary = "Get post's info by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @GetMapping(value = "/post/{post-id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PostResponse getPost(@PathVariable("post-id") UUID postId);

    @Operation(summary = "Creates a post draft")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A post draft created successfully",
                    content = {
                            @Content(schema = @Schema(implementation = UUID.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "The specified attachments not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "The specified attachments have invalid file type",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            )
    })
    @PostMapping(value = "/post", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID createPostDraft(@Parameter(hidden = true) PRINCIPAL account,
                         @RequestBody @Valid PostCreationRequest postCreationRequest);

    @Operation(summary = "Publishes a draft post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "A post draft published successfully",
                    content = {
                            @Content(schema = @Schema(implementation = UUID.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Access denied for post publication",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            )
    })
    @PostMapping(value = "/post/publication", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void publishPostDraft(@Parameter(hidden = true) PRINCIPAL account,
                          @RequestBody @Valid PostRequest postRequest);

    @Operation(summary = "Updates post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Updated post successfully",
                    content = {
                            @Content(schema = @Schema(implementation = UUID.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Illegal post's state",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Access denied for post update",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            )
    })
    @PatchMapping(value = "/post/{post-id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    PostResponse updatePost(@Parameter(hidden = true) PRINCIPAL account,
                            @PathVariable("post-id") UUID postId,
                            @RequestBody @Valid PostUpdateRequest postUpdateRequest);

    @Operation(summary = "Get posts from accounts you follow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class)
                            )
                    })
    })
    @GetMapping(value = "/account/subscription/post", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Page<PostResponse> getPostsFromFollowingAccounts(@Parameter(hidden = true) PRINCIPAL account,
                                                     Pageable pageable);
}
