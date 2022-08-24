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
@Schema(description = "Response with vk account's info")
public class VkAccountResponse {

    @Schema(description = "Vk account id", example = "999999999")
    private Long id;

    @JsonProperty("first_name")
    @Schema(description = "Vk account first name", example = "John")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(description = "Vk account last name", example = "Doe")
    private String lastName;
}

