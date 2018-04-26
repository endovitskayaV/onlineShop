package ru.reksoft.onlineShop.controller.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDataConstructor {
    public static List<Error> getFormErrors(BindingResult bindingResult){
        List<Error> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> errors.add(new Error(fieldError.getField(), fieldError.getDefaultMessage())));

        bindingResult.getAllErrors().forEach(objectError -> {
            if (bindingResult.getFieldErrors().stream().noneMatch(fieldError -> fieldError.equals(objectError)))
                errors.add(new Error("form", objectError.getDefaultMessage()));
        });
        return errors;
    }
}
