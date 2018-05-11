package ru.reksoft.onlineShop.controller.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import ru.reksoft.onlineShop.model.dto.UserDto;
import ru.reksoft.onlineShop.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * creates model from given data
 */
@Service
public class ModelConstructor {
    private UserService userService;

    @Autowired
    public ModelConstructor(UserService userService) {
        this.userService = userService;
    }

    public List<Error> getFormErrors(BindingResult bindingResult) {
        List<Error> errors = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            if (!fieldError.getDefaultMessage().isEmpty()) {
                errors.add(new Error(fieldError.getField(), fieldError.getDefaultMessage()));
            }
        });

        //add errors not belonging to any field
        bindingResult.getAllErrors().forEach(objectError -> {
            //if error is not field error
            if (bindingResult.getFieldErrors().stream().noneMatch(fieldError -> fieldError.equals(objectError)))
                errors.add(new Error("form", objectError.getDefaultMessage()));
        });

        return errors;
    }

    public void setCurrentUser(Model model) {
        model.addAttribute("currentUser", userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

    }
}
