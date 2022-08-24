package ru.asanvlit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.asanvlit.api.SignUpApi;
import ru.asanvlit.dto.request.SignUpRequest;
import ru.asanvlit.service.SignUpService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class SignUpController implements SignUpApi {

    private final SignUpService signUpService;

    @Override
    public UUID signUp(SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
    }
}

