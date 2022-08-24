package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneOauthIllegalStateException extends YartoneIllegalStateException {

    public YartoneOauthIllegalStateException() {
        super("Illegal oauth state");
    }

    public YartoneOauthIllegalStateException(String message) {
        super(message);
    }
}
