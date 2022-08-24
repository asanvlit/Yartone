package ru.asanvlit.dto.response.vk;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response with list of vk users")
public class VkAccountsResponse {

    @Schema(description = "List of vk users")
    private List<VkAccountResponse> response;
}

