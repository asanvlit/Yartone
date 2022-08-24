package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneNotFoundException extends YartoneServiceException {

    public YartoneNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public YartoneNotFoundException(Throwable cause, HttpStatus httpStatus) {
        super (cause, HttpStatus.NOT_FOUND);
    }
}

