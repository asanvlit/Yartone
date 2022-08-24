package ru.asanvlit.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class YartoneJwtExpiredException extends AuthenticationException {

    public YartoneJwtExpiredException(String message) {
        super(message);
    }
}
