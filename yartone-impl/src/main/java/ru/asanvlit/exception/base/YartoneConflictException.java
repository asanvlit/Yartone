package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneConflictException extends YartoneServiceException {

    public YartoneConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
