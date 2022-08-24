package ru.asanvlit.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {

    POST_ATTACHMENT("Post's attachment"),
    PROFILE_PHOTO("Account's avatar");

    private final String description;
}
