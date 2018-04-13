package ru.reksoft.onlineShop.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = RequiredCharacteristicNotEmptyValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredCharacteristicNotEmpty {

    String message() default "Fill in required characteristic";

    String characteristicValue();

    String required();
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}