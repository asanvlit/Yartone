package ru.asanvlit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with main account info")
public class AccountResponse {

    @Schema(description = "Account's id", example = "9e698189-ac27-4897-805f-be9d42499f2b")
    private UUID id;

    @Schema(description = "Username", example = "test_user")
    private String username;

    @Schema(description = "Name", example = "John")
    private String name;
}
