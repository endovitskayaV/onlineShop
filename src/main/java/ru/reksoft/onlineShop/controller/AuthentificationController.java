package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.reksoft.onlineShop.controller.util.ClientDataConstructor;
import ru.reksoft.onlineShop.controller.util.Error;
import ru.reksoft.onlineShop.model.dto.LoginUserDto;
import ru.reksoft.onlineShop.model.dto.SignupUserDto;
import ru.reksoft.onlineShop.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthentificationController {
    private static final String DEFAULT_DESTINATION = "/items";
    private static final int CUSTOMER_ROLE_ID = 2;
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public AuthentificationController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(ModelMap model, @RequestParam(required = false) String destination) {
        setLoginUserModel(model, LoginUserDto.builder().email("").password("").build(), destination);

        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(ModelMap modelMap,
                              @Valid LoginUserDto loginUserDto,
                              BindingResult bindingResult,
                              @RequestParam String destination) {

        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);
        if (errors.size() == 0) {
            if (!login(loginUserDto.getEmail(), loginUserDto.getPassword())) {
                errors.add(new Error("form", "Wrong email or password"));
            }
        }

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setLoginUserModel(modelMap, loginUserDto, destination);
            return new ModelAndView("login");
        } else {
            return new ModelAndView("redirect:" + destination);
        }

    }

    @GetMapping("/signup")
    public String signup(ModelMap model, @RequestParam(required = false) String destination) {
        setSignupUserModel(model,
                SignupUserDto.builder().email("").password("").confirmPassword("").roleId(CUSTOMER_ROLE_ID).build(),
                destination);
        return "signup";
    }

    @PostMapping("/signup")
    public ModelAndView signup(ModelMap modelMap,
                               @Valid SignupUserDto signupUserDto,
                               BindingResult bindingResult,
                               @RequestParam(required = false) String destination) {

        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);

        if (errors.size() == 0) {
            if (!userService.add(signupUserDto)) {
                errors.add(new Error("form", "User with such email already exists"));
            }
        }

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setSignupUserModel(modelMap, signupUserDto, destination);
            return new ModelAndView("signup");
        } else {
            login(signupUserDto.getEmail(), signupUserDto.getPassword());
            return new ModelAndView("redirect:" + (destination == null ? DEFAULT_DESTINATION : destination));
        }
    }


    private void setLoginUserModel(ModelMap modelMap, LoginUserDto loginUserDto, String destination) {
        modelMap.addAttribute("user", LoginUserDto.builder().email(loginUserDto.getEmail()).password("").build());
        modelMap.addAttribute("destination", destination == null ? DEFAULT_DESTINATION : destination);
    }

    private void setSignupUserModel(ModelMap modelMap, SignupUserDto signupUserDto, String destination) {
        modelMap.addAttribute("user", SignupUserDto.builder()
                .email(signupUserDto.getEmail())
                .roleId(signupUserDto.getRoleId())
                .confirmPassword("")
                .password("")
                .build());
        modelMap.addAttribute("destination", destination == null ? DEFAULT_DESTINATION : destination);
    }

    private boolean login(String email, String password) {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(email, password);

        try {
            Authentication authentication = authenticationManager.authenticate(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            return false;
        }
        return true;
    }
}
