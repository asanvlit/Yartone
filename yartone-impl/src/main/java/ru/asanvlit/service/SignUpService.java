package ru.asanvlit.service;

import ru.asanvlit.dto.request.SignUpRequest;

import java.util.UUID;

public interface SignUpService {

    UUID signUp(SignUpRequest signUpRequest);
}
