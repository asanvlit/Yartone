package ru.asanvlit.exception.notfound;

import ru.asanvlit.exception.base.YartoneNotFoundException;

public class YartoneConfirmCodeNotFoundException extends YartoneNotFoundException {

    public YartoneConfirmCodeNotFoundException() {
        super("Confirm-code not found");
    }

    public YartoneConfirmCodeNotFoundException(String message) {
        super(message);
    }
}
