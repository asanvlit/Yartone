package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneIllegalArgumentException extends YartoneServiceException {

    public YartoneIllegalArgumentException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public YartoneIllegalArgumentException(Throwable cause, HttpStatus httpStatus) {
        super (cause, httpStatus);
    }
}
