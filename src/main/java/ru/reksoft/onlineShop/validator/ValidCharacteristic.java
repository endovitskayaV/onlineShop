package ru.reksoft.onlineShop.validator;


import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

//import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = CharacteristicValidator.class)
//@Documented
public @interface ValidCharacteristic {

    boolean required();
    String message() default "Fill in chracteristic value";


//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//
//    int min() default 5;
}
