package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneValidationException extends YartoneServiceException {

    public YartoneValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public YartoneValidationException(Throwable cause, HttpStatus httpStatus) {
        super (cause, HttpStatus.BAD_REQUEST);
    }
}
