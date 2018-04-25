package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public AuthentificationController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService=userService;
    }

    @GetMapping("/login")
    public String login(ModelMap model) {
        setLoginUserModel(model, LoginUserDto.builder().email("").password("").build());
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(ModelMap modelMap, @Valid LoginUserDto loginUserDto, BindingResult bindingResult) {
        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);
        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setLoginUserModel(modelMap, loginUserDto);
            return new ModelAndView("login");
        } else {
           UsernamePasswordAuthenticationToken user=new UsernamePasswordAuthenticationToken(
                   loginUserDto.getEmail(),
                   loginUserDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // errors.add(new Error("price", "Price must be greater than 0"));

        //    userService.loadUserByUsername()


            return new ModelAndView("redirect:/items");
        }
    }

    @GetMapping("/signup")
    public String signup(ModelMap model) {
        setSignupUserModel(model, SignupUserDto.builder().email("").password("").confirmPassword("").roleId(0).build());
        return "signup";
    }

    @PostMapping("/signup")
    public ModelAndView signup(ModelMap modelMap, @Valid SignupUserDto signupUserDto, BindingResult bindingResult) {
        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);
        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setSignupUserModel(modelMap, signupUserDto);
            return new ModelAndView("signup");
        } else {
            //TODO: signup here and login
            return new ModelAndView("redirect:/items");
        }
    }


    private void setLoginUserModel(ModelMap modelMap, LoginUserDto loginUserDto) {
        modelMap.addAttribute("user", loginUserDto);
    }

    private void setSignupUserModel(ModelMap modelMap, SignupUserDto signupUserDto) {
        modelMap.addAttribute("user", signupUserDto);
    }
    //TODO: make logout
}
