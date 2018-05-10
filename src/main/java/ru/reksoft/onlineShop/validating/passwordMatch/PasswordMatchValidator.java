package ru.reksoft.onlineShop.validating.passwordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, PasswordsProvider> {
    @Override
    public boolean isValid(PasswordsProvider passwordsProvider, ConstraintValidatorContext context) {
     return passwordsProvider.getPassword().equals(passwordsProvider.getConfirmPassword());
    }
}
