package ru.asanvlit.exception.notfound;

import ru.asanvlit.exception.base.YartoneNotFoundException;

public class YartoneConfirmCodeTypeNotFoundException extends YartoneNotFoundException {

    public YartoneConfirmCodeTypeNotFoundException() {
        super("Confirm code type not found");
    }

    public YartoneConfirmCodeTypeNotFoundException(String message) {
        super(message);
    }
}
