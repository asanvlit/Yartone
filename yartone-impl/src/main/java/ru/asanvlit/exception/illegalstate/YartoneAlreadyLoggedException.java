package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneConflictException;
import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneAlreadyLoggedException extends YartoneIllegalStateException {

    public YartoneAlreadyLoggedException(String message) {
        super(message);
    }
}
