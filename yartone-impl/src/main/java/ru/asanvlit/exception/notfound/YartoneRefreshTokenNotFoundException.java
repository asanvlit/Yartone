package ru.asanvlit.exception.notfound;

import ru.asanvlit.exception.base.YartoneNotFoundException;

public class YartoneRefreshTokenNotFoundException extends YartoneNotFoundException {

    public YartoneRefreshTokenNotFoundException() {
        super("Refresh token not found");
    }

    public YartoneRefreshTokenNotFoundException(String message) {
        super(message);
    }
}
