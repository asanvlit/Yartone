package ru.asanvlit.config;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.asanvlit.dto.request.EmailPasswordRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.asanvlit.constant.YartoneImplConstants.AUTHENTICATION_URL;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(buildInfo())
                .paths(buildPaths())
                .components(buildComponents())
                .security(buildSecurity());
    }

    private Info buildInfo() {
        return new Info()
                .title("Yartone API")
                .description("API's for Yartone art platform")
                .version("v1");
    }

    private Components buildComponents() {
        return new Components()
                .schemas(new HashMap<>() {{
                             putAll(ModelConverters.getInstance().read(EmailPasswordRequest.class));
                         }}
                )
                .addSecuritySchemes("Bearer", new SecurityScheme()
                        .name("BearerAuth")
                        .description("JWT Authorization header using the Bearer scheme. Example: \\\"Authorization: Bearer {token}\\\"")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
    }

    private List<SecurityRequirement> buildSecurity() {
        return Collections.singletonList(new SecurityRequirement().addList("Bearer"));
    }

    private Paths buildPaths() {
        return new Paths()
                .addPathItem(AUTHENTICATION_URL, buildPathItem());
    }

    private PathItem buildPathItem() {
        return new PathItem().post(
                new Operation()
                        .addTagsItem("Authentication")
                        .requestBody(buildAuthenticationRequestBody())
                        .responses(buildAuthenticationResponses()));
    }

    private RequestBody buildAuthenticationRequestBody() {
        return new RequestBody().content(
                new Content()
                        .addMediaType(APPLICATION_JSON_VALUE,
                                new MediaType()
                                        .schema(new Schema<>()
                                                .$ref("#/components/schemas/EmailPasswordRequest")
                                        )
                        )
        );
    }

    private ApiResponses buildAuthenticationResponses() {
        return new ApiResponses()
                .addApiResponse("200",
                        new ApiResponse()
                                .description("OK")
                                .content(new Content()
                                        .addMediaType("application/json",
                                                new MediaType()
                                                        .schema(new Schema<>()
                                                                .$ref("#/components/schemas/AccessRefreshTokenPairResponse")
                                                        )
                                        )
                                )
                ).addApiResponse("400",
                        new ApiResponse()
                                .description("The login has already been")
                                .content(new Content()
                                        .addMediaType("application/json",
                                                new MediaType()
                                                        .schema(new Schema<>()
                                                                .$ref("#/components/schemas/ExceptionResponse")
                                                        )
                                        )
                                )
                ).addApiResponse("401",
                        new ApiResponse()
                                .description("Illegal email or password (null/ not-existing account/ illegal password)")
                                .content(new Content()
                                        .addMediaType("application/json",
                                                new MediaType()
                                                        .schema(new Schema<>()
                                                                .$ref("#/components/schemas/ExceptionResponse")
                                                        )
                                        )
                                )
                )
                .addApiResponse("403",
                        new ApiResponse()
                                .description("Invalid account status (ex. BANNED)")
                                .content(new Content()
                                        .addMediaType("application/json",
                                                new MediaType()
                                                        .schema(new Schema<>()
                                                                .$ref("#/components/schemas/ExceptionResponse")
                                                        )
                                        )
                                )
                );
    }
}
