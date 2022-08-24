package ru.asanvlit.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static ru.asanvlit.constant.YartoneApiConstants.ACCOUNT_BIOGRAPHY_MAX_LENGTH;
import static ru.asanvlit.constant.YartoneApiConstants.NAME_PATTERN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to update account's main info")
public class AccountUpdateRequest {

    @Pattern(regexp = NAME_PATTERN, message = "{accountUpdateRequest.validation.name}")
    @Schema(description = "Name", example = "John")
    private String name;

    @Length(max = ACCOUNT_BIOGRAPHY_MAX_LENGTH)
    @Schema(description = "Biography")
    private String biography;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Birthdate", example = "2022-07-12")
    private LocalDate birthdate;
}
