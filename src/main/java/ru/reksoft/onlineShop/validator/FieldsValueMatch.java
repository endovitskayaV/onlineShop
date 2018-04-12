package ru.reksoft.onlineShop.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//@Target(FIELD)
//@Retention(RUNTIME)
//@Constraint(validatedBy = CharacteristicValidator.class)
////@Documented
//public @interface ValidCharacteristic {
//
//    boolean required();
//    String message() default "Fill in chracteristic value";
//
//
////    Class<?>[] groups() default {};
////
////    Class<? extends Payload>[] payload() default {};
////
////    int min() default 5;
//}



@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {

    String message() default "Fill in required characteristic";

    String characteristicValue();

    String required();
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
