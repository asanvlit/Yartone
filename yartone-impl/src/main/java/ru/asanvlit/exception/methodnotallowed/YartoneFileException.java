package ru.asanvlit.exception.methodnotallowed;

import ru.asanvlit.exception.base.YartoneMethodNotAllowedException;

public class YartoneFileException extends YartoneMethodNotAllowedException {

    public YartoneFileException(String message) {
        super(message);
    }
}
