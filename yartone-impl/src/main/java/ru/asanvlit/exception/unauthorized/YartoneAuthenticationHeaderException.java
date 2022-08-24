package ru.asanvlit.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class YartoneAuthenticationHeaderException extends AuthenticationException {

    public YartoneAuthenticationHeaderException(String message) {
        super(message);
    }

    public YartoneAuthenticationHeaderException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
