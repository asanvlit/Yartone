package ru.asanvlit.validation.annotation;

import ru.asanvlit.validation.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.asanvlit.constant.YartoneApiConstants.PASSWORD_PATTERN;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidPassword {

    String message() default "{isValidPassword.validation.defaultMessage}" + " " + PASSWORD_PATTERN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

