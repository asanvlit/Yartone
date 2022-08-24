package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneMethodNotAllowedException extends YartoneServiceException {

    public YartoneMethodNotAllowedException(String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }

    public YartoneMethodNotAllowedException(Throwable cause, HttpStatus httpStatus) {
        super (cause, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
