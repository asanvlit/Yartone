package ru.asanvlit.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfirmCodeType {

    ACCOUNT_CONFIRM("Account email confirmation"),
    PASSWORD_RESET("Code to confirm the password change action");

    private final String description;

}
