package ru.asanvlit.exception.notfound;

import ru.asanvlit.exception.base.YartoneNotFoundException;

public class YartonePostNotFoundException extends YartoneNotFoundException {

    public YartonePostNotFoundException() {
        super("Post not found");
    }

    public YartonePostNotFoundException(String message) {
        super(message);
    }
}

