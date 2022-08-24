package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneRefreshTokenException extends YartoneServiceException {

    public YartoneRefreshTokenException(String token, String message) {
        super(HttpStatus.UNAUTHORIZED, String.format("Failed for [%s]: %s", token, message));
    }
}

