package ru.asanvlit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request with access and refresh tokens")
public class TokenPairRequest {

    @NotBlank
    @Schema(description = "Access token", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsImV4cCI6MTY1Nzg4ODk1NiwiaWF0IjoxNjU3ODg1MzU2LCJlbWFpbCI6InRlc3RAZ21haWwuY29tIn0" +
            ".dgl30eJrdyvWH7UzDIV_aIFGlKtEKylWXybyH38OXLA")
    private String accessToken;

    @NotBlank
    @Schema(description = "Refresh token", example = "9e698189-ac27-4897-805f-be9d42499f2b")
    private String refreshToken;
}

