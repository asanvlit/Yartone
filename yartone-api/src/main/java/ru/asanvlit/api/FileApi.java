package ru.asanvlit.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import ru.asanvlit.dto.request.FileUploadRequest;
import ru.asanvlit.dto.response.ExceptionResponse;
import ru.asanvlit.dto.response.FileInfoResponse;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static ru.asanvlit.constant.YartoneApiConstants.API;

@RequestMapping(API + "/file")
public interface FileApi {

    @Operation(summary = "Get file's content by file-info id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Not existing file-info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "405", description = "Failed to get file data from content storage",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            )
    })
    @GetMapping(value = "/{id}/content")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Resource> getFileResource(@PathVariable("id") UUID fileId);

    @Operation(summary = "Get file-info by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FileInfoResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Not existing file-info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @GetMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    FileInfoResponse getFileInfo(@PathVariable("id") UUID fileId);

    @Operation(summary = "Uploads file to the server")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "File uploaded successfully",
                    content = {
                            @Content(schema = @Schema(implementation = UUID.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Invalid file extension for the file type",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "405", description = "Failed to get file data/ Failed to upload file to storage",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    }
            )
    })
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UUID uploadFile(@ModelAttribute @Valid FileUploadRequest file);
}
