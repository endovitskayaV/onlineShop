package ru.reksoft.onlineShop.controller.util;


import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class ClientDataConstructor {
    public static List<Error> getFormErrors(BindingResult bindingResult) {
        List<Error> errors = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldError -> errors.add(new Error(fieldError.getField(), fieldError.getDefaultMessage())));

        //add errors not belonging to any field
        bindingResult.getAllErrors().forEach(objectError -> {
            //if error is not field error
            if (bindingResult.getFieldErrors().stream().noneMatch(fieldError -> fieldError.equals(objectError)))
                errors.add(new Error("form", objectError.getDefaultMessage()));
        });

        return errors;
    }
}
