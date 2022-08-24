package ru.asanvlit.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountState {

    CREATED("A just created account"),
    NOT_CONFIRMED("A not-confirmed account"),
    ACTIVE("An active confirmed account"),
    BANNED("Banned account"),
    DELETED("Deleted account");

    private final String description;
}
