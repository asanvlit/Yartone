package ru.asanvlit.exception.notfound;

import ru.asanvlit.exception.base.YartoneNotFoundException;

public class YartoneFileNotFoundException extends YartoneNotFoundException {

    public YartoneFileNotFoundException() {
        super("File not found");
    }

    public YartoneFileNotFoundException(String message) {
        super(message);
    }
}

