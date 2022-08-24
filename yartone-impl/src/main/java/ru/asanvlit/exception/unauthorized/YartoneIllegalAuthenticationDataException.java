package ru.asanvlit.exception.unauthorized;

import ru.asanvlit.exception.base.YartoneAuthenticationException;

public class YartoneIllegalAuthenticationDataException extends YartoneAuthenticationException {

    public YartoneIllegalAuthenticationDataException() {
        super("Illegal authentication data");
    }

    public YartoneIllegalAuthenticationDataException(String message) {
        super(message);
    }
}
