package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneTokenException extends YartoneServiceException {

    public YartoneTokenException(String token, String message) {
        super(HttpStatus.UNAUTHORIZED, String.format("Failed for [%s]: %s", token, message));
    }
}
