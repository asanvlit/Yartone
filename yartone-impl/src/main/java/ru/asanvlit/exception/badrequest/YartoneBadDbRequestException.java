package ru.asanvlit.exception.badrequest;

import ru.asanvlit.exception.base.YartoneBadRequestException;

public class YartoneBadDbRequestException extends YartoneBadRequestException {

    public YartoneBadDbRequestException() {
        super("Bad db request");
    }
}
