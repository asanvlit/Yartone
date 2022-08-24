package ru.asanvlit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with access + refresh tokens pair")
public class AccessRefreshTokenPairResponse {

    @Schema(description = "Access token", example = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsImV4cCI6MTY1Nzg4ODk1NiwiaWF0IjoxNjU3ODg1MzU2LCJlbWFpbCI6InRlc3RAZ21haWwuY29tIn0" +
            ".dgl30eJrdyvWH7UzDIV_aIFGlKtEKylWXybyH38OXLA")
    private String accessToken;

    @Schema(description = "Refresh token", example = "9e698189-ac27-4897-805f-be9d42499f2b")
    private String refreshToken;

    @Schema(description = "Access token expiration date", example = "1657871611000")
    private Date accessTokenExpirationDate;
}

