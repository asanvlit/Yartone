package ru.asanvlit.validation.validator;

import org.springframework.beans.BeanWrapperImpl;
import ru.asanvlit.validation.annotation.EqualPasswords;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords, Object> {

    private String passwordValue;

    private String passwordRepeatValue;

    @Override
    public void initialize(EqualPasswords constraintAnnotation) {
        this.passwordValue = constraintAnnotation.password();
        this.passwordRepeatValue= constraintAnnotation.passwordRepeat();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object password = new BeanWrapperImpl(o).getPropertyValue(passwordValue);
        Object passwordRepeat = new BeanWrapperImpl(o).getPropertyValue(passwordRepeatValue);

        boolean isValid = (password != null && password.equals(passwordRepeat));
        if (!isValid) {
            buildConstraint("Password and password repeat are not the same", "password", constraintValidatorContext);
        }

        return isValid;
    }

    private void buildConstraint(String template, String node, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(template)
                .addPropertyNode(node)
                .addConstraintViolation();
    }
}
