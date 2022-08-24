package ru.asanvlit.exception.methodnotallowed;

import ru.asanvlit.exception.base.YartoneMethodNotAllowedException;

public class YartoneTemplatesMethodException extends YartoneMethodNotAllowedException{

    public YartoneTemplatesMethodException() {
        super("Can't generate template");
    }

    public YartoneTemplatesMethodException(String message) {
        super(message);
    }
}
