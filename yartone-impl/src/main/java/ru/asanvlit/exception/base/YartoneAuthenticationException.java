package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneAuthenticationException extends YartoneServiceException {

    public YartoneAuthenticationException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
