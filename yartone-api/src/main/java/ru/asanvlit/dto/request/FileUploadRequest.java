package ru.asanvlit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.asanvlit.dto.enums.FileType;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to upload new file")
public class FileUploadRequest {

    @NotNull
    @Schema(description = "Files's type", example = "PROFILE_PHOTO")
    private FileType fileType;

    @NotNull
    @Schema(description = "File's content")
    private MultipartFile fileContent;
}

