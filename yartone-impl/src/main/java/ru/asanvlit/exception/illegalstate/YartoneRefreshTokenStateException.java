package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneRefreshTokenStateException extends YartoneIllegalStateException {

    public YartoneRefreshTokenStateException() {
        super("Illegal refresh token state");
    }

    public YartoneRefreshTokenStateException(String message) {
        super(message);
    }
}

