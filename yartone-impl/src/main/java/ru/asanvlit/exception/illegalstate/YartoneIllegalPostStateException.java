package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneIllegalPostStateException  extends YartoneIllegalStateException {

    public YartoneIllegalPostStateException() {
        super("Illegal post's state");
    }

    public YartoneIllegalPostStateException(String message) {
        super(message);
    }
}

