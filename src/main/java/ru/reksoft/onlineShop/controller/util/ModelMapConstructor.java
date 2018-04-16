package ru.reksoft.onlineShop.controller.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapConstructor {
    public static void performFormErrors(ModelMap modelMap, BindingResult bindingResult){
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        modelMap.addAttribute("errors", errors);
    }
}
