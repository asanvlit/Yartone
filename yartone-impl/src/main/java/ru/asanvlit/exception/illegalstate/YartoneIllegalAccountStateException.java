package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneIllegalAccountStateException extends YartoneIllegalStateException {

    public YartoneIllegalAccountStateException() {
        super("Illegal account state");
    }

    public YartoneIllegalAccountStateException(String message) {
        super(message);
    }
}

