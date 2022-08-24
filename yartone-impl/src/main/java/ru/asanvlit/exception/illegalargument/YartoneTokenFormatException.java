package ru.asanvlit.exception.illegalargument;

import ru.asanvlit.exception.base.YartoneIllegalArgumentException;

public class YartoneTokenFormatException extends YartoneIllegalArgumentException {

    public YartoneTokenFormatException() {
        super("Bad token format");
    }

    public YartoneTokenFormatException(String message) {
        super(message);
    }
}
