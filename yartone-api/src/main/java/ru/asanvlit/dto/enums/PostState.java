package ru.asanvlit.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostState {

    DRAFT("Draft"),
    ACTIVE("Active"),
    DELETED("Deleted"),
    BANNED("Banned");

    private final String description;
}
