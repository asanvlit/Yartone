package ru.asanvlit.data.constant;

import ru.asanvlit.dto.request.SignUpRequest;

public interface YartoneTestConstants {

    long SIGN_UP_TEST_COUNT = 5;

    SignUpRequest DEFAULT_SIGN_UP_REQUEST = SignUpRequest.builder()
            .email("@t@gmail.com")
            .username("t")
            .build();
}
