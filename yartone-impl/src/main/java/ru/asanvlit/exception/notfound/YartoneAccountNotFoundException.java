package ru.asanvlit.exception.notfound;

import ru.asanvlit.exception.base.YartoneNotFoundException;

public class YartoneAccountNotFoundException extends YartoneNotFoundException {

    public YartoneAccountNotFoundException() {
        super("Account not found");
    }

    public YartoneAccountNotFoundException(String message) {
        super(message);
    }
}

