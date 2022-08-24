package ru.asanvlit.exception.methodnotallowed;

import ru.asanvlit.exception.base.YartoneMethodNotAllowedException;

public class YartoneEmailException extends YartoneMethodNotAllowedException {

    public YartoneEmailException(String message) {
        super(message);
    }
}
