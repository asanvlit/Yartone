package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneAlreadyExistingConfirmCodeException extends YartoneIllegalStateException {

    public YartoneAlreadyExistingConfirmCodeException() {
        super("Such valid confirm code for the account already exists");
    }
}
