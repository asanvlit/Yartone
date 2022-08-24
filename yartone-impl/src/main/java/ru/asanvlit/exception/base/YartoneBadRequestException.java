package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneBadRequestException extends YartoneServiceException {

    public YartoneBadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public YartoneBadRequestException(Throwable cause) {
        super(cause, HttpStatus.BAD_REQUEST);
    }
}
