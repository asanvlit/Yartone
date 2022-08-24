package ru.asanvlit.exception.base;

import org.springframework.http.HttpStatus;

public class YartoneAccessDeniedException extends YartoneServiceException {

    public YartoneAccessDeniedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
