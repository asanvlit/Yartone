package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneIllegalStateException extends YartoneServiceException {

    public YartoneIllegalStateException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public YartoneIllegalStateException(Throwable cause, HttpStatus httpStatus) {
        super (cause, HttpStatus.BAD_REQUEST);
    }
}
