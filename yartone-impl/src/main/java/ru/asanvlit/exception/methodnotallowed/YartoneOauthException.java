package ru.asanvlit.exception.methodnotallowed;

import ru.asanvlit.exception.base.YartoneMethodNotAllowedException;

public class YartoneOauthException extends YartoneMethodNotAllowedException{

    public YartoneOauthException(String message) {
        super(message);
    }
}
