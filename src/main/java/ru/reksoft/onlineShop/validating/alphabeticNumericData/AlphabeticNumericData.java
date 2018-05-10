package ru.reksoft.onlineShop.validating.alphabeticNumericData;

import ru.reksoft.onlineShop.validating.characteristicValueDataType.CheckCharacteristicValueDataTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AlphabeticNumericDataValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AlphabeticNumericData {

    String message() default "Only alphabetic symbols and numbers allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

