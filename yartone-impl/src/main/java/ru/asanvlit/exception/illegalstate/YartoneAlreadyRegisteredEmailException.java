package ru.asanvlit.exception.illegalstate;

import ru.asanvlit.exception.base.YartoneIllegalStateException;

public class YartoneAlreadyRegisteredEmailException extends YartoneIllegalStateException {

    public YartoneAlreadyRegisteredEmailException() {
        super("The account with this email has been already registered");
    }
}
