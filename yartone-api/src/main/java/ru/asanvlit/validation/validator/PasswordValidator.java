package ru.asanvlit.validation.validator;

import ru.asanvlit.validation.annotation.IsValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static ru.asanvlit.constant.YartoneApiConstants.PASSWORD_MIN_LENGTH;
import static ru.asanvlit.constant.YartoneApiConstants.PASSWORD_PATTERN;

public class PasswordValidator implements ConstraintValidator<IsValidPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.trim().length() > PASSWORD_MIN_LENGTH && value.matches(PASSWORD_PATTERN);
    }
}
