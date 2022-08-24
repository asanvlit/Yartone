package ru.asanvlit.dto.response.vk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Vk response with user info for getting oauth token")
public class VkAccountAccessTokenResponse {

    @JsonProperty("user_id")
    @Schema(description = "Vk account id", example = "999999999")
    private Long userId;

    @JsonProperty("access_token")
    @Schema(description = "Vk account access token")
    private String accessToken;

    @JsonProperty("expires_in")
    @Schema(description = "When vk token becomes invalid")
    private Integer expiresIn;

    @Schema(description = "Vk account's email", example = "test@gmail.com")
    private String email;
}

