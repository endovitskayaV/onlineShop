package ru.reksoft.onlineShop.validating.characteristicValueDataType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = CheckCharacteristicValueDataTypeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckCharacteristicValueDataType {

    String message() default "Wrong data type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

