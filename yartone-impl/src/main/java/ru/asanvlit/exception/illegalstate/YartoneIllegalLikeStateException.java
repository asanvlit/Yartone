package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneIllegalLikeStateException   extends YartoneIllegalStateException {

    public YartoneIllegalLikeStateException() {
        super("Illegal like state");
    }

    public YartoneIllegalLikeStateException(String message) {
        super(message);
    }
}

