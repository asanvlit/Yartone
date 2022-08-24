package ru.asanvlit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.asanvlit.data.provider.SignUpDataProvider;
import ru.asanvlit.dto.request.SignUpRequest;
import ru.asanvlit.security.filter.JwtAuthenticationFilter;
import ru.asanvlit.security.filter.JwtAuthorizationFilter;
import ru.asanvlit.service.SignUpService;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.asanvlit.constant.YartoneApiConstants.API;
import static ru.asanvlit.constant.YartoneTestConstants.DEFAULT_SIGN_UP_REQUEST;

@WebMvcTest(SignUpController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("SignUpController is working:")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SignUpService signUpService;

    @MockBean
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        when(signUpService.signUp(any(SignUpRequest.class))).thenReturn(UUID.randomUUID());
    }

    @DisplayName(("signUp() is working:"))
    @Nested
    class ForSignUp {

        @ParameterizedTest(name = "return 201 on {0}")
        @ArgumentsSource(value = SignUpDataProvider.class)
        public void return_201_on_valid_sign_up_data(SignUpRequest signUpRequest) throws Exception {
            mockMvc.perform(post(API + "/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)))
            .andExpect(status().isCreated());
        }

        @Test
        public void return_400_on_invalid_data() throws Exception {
            mockMvc.perform(post(API + "/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(SignUpRequest.builder()
                            .email("@t@gmail.com")
                            .username("t")
                            .build()
                    )))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("errors", hasSize(2)));
        }

        @Test
        public void return_400_on_empty_fields() throws Exception {
            mockMvc.perform(post(API + "/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(SignUpRequest.builder()
                            .username("test")
                            .build()
                    )))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("errors", hasSize(1)));
        }

        @Test
        public void return_400_on_not_supported_method() throws Exception {
            mockMvc.perform(get(API + "/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(DEFAULT_SIGN_UP_REQUEST)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void return_400_on_invalid_media_type() throws Exception {
            mockMvc.perform(post(API + "/sign-up")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .content(objectMapper.writeValueAsString(DEFAULT_SIGN_UP_REQUEST)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void return_400_on_invalid_json_format() throws Exception {
            mockMvc.perform(post(API + "/sign-up")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .content("{\n" +
                            "    \"username\" \"test\",\n" +
                            "    \"email\" \"test1@gmail.com\"\n" +
                            "}"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void return_400_on_invalid_field_names() throws Exception {
            mockMvc.perform(post(API + "/sign-up")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .content("{\n" +
                            "    \"name\": \"test\",\n" +
                            "    \"name2\": \"test1gmail.com\"\n" +
                            "}"))
                    .andExpect(status().isBadRequest());
        }
    }
}

