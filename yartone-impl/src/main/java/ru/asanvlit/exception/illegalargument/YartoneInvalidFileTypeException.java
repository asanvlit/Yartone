package ru.asanvlit.exception.illegalargument;

import ru.asanvlit.exception.base.YartoneIllegalArgumentException;

public class YartoneInvalidFileTypeException extends YartoneIllegalArgumentException {

    public YartoneInvalidFileTypeException() {
        super("Not acceptable file type for this action");
    }

    public YartoneInvalidFileTypeException(String message) {
        super(message);
    }
}
