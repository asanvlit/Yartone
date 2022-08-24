package ru.asanvlit.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class YartoneServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public YartoneServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public YartoneServiceException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.httpStatus = httpStatus;
    }
}

