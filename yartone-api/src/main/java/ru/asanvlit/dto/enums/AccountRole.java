package ru.asanvlit.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountRole {

    USER("User"),
    ADMIN("Admin");

    private final String description;
}
