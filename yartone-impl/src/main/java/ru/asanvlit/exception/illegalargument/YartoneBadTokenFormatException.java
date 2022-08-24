package ru.asanvlit.exception.illegalargument;

import ru.asanvlit.exception.base.YartoneIllegalArgumentException;

public class YartoneBadTokenFormatException extends YartoneIllegalArgumentException {

    public YartoneBadTokenFormatException() {
        super("Bad token format");
    }

    public YartoneBadTokenFormatException(String message) {
        super(message);
    }
}
