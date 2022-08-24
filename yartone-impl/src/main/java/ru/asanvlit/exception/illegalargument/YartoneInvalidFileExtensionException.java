package ru.asanvlit.exception.illegalargument;

import ru.asanvlit.exception.base.YartoneIllegalArgumentException;

public class YartoneInvalidFileExtensionException extends YartoneIllegalArgumentException {

    public YartoneInvalidFileExtensionException() {
        super("Not acceptable file extension for this type of file");
    }

    public YartoneInvalidFileExtensionException(String message) {
        super(message);
    }
}
