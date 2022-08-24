package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneExpiredConfirmCodeException extends YartoneIllegalStateException {

    public YartoneExpiredConfirmCodeException() {
        super("The confirmation code has already expired");
    }

    public YartoneExpiredConfirmCodeException(String message) {
        super(message);
    }
}
