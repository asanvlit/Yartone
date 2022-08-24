package ru.asanvlit.validation.annotation;

import ru.asanvlit.validation.validator.EqualPasswordsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Constraint(validatedBy = EqualPasswordsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualPasswords {

    String password();

    String passwordRepeat();

    String message() default "{equalPasswords.validation.defaultMessage}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
