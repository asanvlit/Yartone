package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneUnauthorizedException extends YartoneServiceException {

    public YartoneUnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public YartoneUnauthorizedException(Throwable cause, HttpStatus httpStatus) {
        super (cause, HttpStatus.UNAUTHORIZED);
    }
}
