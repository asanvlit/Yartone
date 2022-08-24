package ru.asanvlit.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class YartoneAuthenticationSchemeException extends AuthenticationException {

    public YartoneAuthenticationSchemeException(String message) {
        super(message);
    }

    public YartoneAuthenticationSchemeException(String msg, Throwable t) {
        super(msg, t);
    }
}
