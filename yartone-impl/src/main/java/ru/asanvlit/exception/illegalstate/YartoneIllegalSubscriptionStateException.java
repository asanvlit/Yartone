package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneIllegalSubscriptionStateException extends YartoneIllegalStateException {

    public YartoneIllegalSubscriptionStateException() {
        super("Illegal subscription state");
    }

    public YartoneIllegalSubscriptionStateException(String message) {
        super(message);
    }
}

