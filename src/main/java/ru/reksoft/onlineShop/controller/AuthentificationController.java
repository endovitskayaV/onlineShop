package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.reksoft.onlineShop.controller.util.ClientDataConstructor;
import ru.reksoft.onlineShop.controller.util.CookiesUtils;
import ru.reksoft.onlineShop.controller.util.Error;
import ru.reksoft.onlineShop.model.dto.LoginUserDto;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.model.dto.SignupUserDto;
import ru.reksoft.onlineShop.service.OrderService;
import ru.reksoft.onlineShop.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static ru.reksoft.onlineShop.controller.util.CookiesUtils.*;
import static ru.reksoft.onlineShop.service.UserService.ROLE_PREFIX;

@Controller
public class AuthentificationController {
    private static final String DEFAULT_DESTINATION = "/items";
    private static final int CUSTOMER_ROLE_ID = 2;
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public AuthentificationController(AuthenticationManager authenticationManager, UserService userService, OrderService orderService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/login")
    public String login(ModelMap model, @RequestParam(required = false) String destination, HttpServletRequest request) {

        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        model.addAttribute("cookies", cookies);

        setLoginUserModel(model, LoginUserDto.builder().email("").password("").build(), destination);

        return "login";
    }

    @PostMapping("/login")
    public String login(ModelMap modelMap,
                        @Valid LoginUserDto loginUserDto,
                        BindingResult bindingResult,
                        @RequestParam String destination,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {

        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);
        if (errors.size() == 0) {
            if (!login(loginUserDto.getEmail(), loginUserDto.getPassword())) {
                errors.add(new Error("form", "Wrong email or password"));
            }
        }

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setLoginUserModel(modelMap, loginUserDto, destination);
            return "login";
        } else {
            saveBasketFromCookiesToDb(
                    userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId(),
                    cookies);

            deleteBasketCookies(cookies);
            redirectAttributes.addFlashAttribute("cookies", deleteBasketCookies(cookies));
            return "redirect:" + destination;
        }

    }

    @GetMapping("/signup")
    public String signup(ModelMap model, @RequestParam(required = false) String destination, HttpServletRequest request) {
        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        model.addAttribute("cookies", cookies);

        setSignupUserModel(model,
                SignupUserDto.builder().email("").password("").confirmPassword("").roleId(CUSTOMER_ROLE_ID).build(),
                destination);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(ModelMap modelMap,
                         @Valid SignupUserDto signupUserDto,
                         BindingResult bindingResult,
                         @RequestParam(required = false) String destination, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);

        if (errors.size() == 0) {
            if (!userService.add(signupUserDto)) {
                errors.add(new Error("form", "User with such email already exists"));
            }
        }

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setSignupUserModel(modelMap, signupUserDto, destination);
            return "signup";
        } else {
            login(signupUserDto.getEmail(), signupUserDto.getPassword());
            saveBasketFromCookiesToDb(
                    userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId(),
                    cookies);

            deleteBasketCookies(cookies);
            redirectAttributes.addFlashAttribute("cookies", deleteBasketCookies(cookies));
            return "redirect:" + (destination == null ? DEFAULT_DESTINATION : destination);
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
        try {
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(email, password,
                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + userService.getRoleByEmail(email).getName().toUpperCase())));
            Authentication authentication = authenticationManager.authenticate(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException  | NullPointerException e) {
            return false;
        }
        return true;
    }

    private void saveBasketFromCookiesToDb(long userId, List<Cookie> cookies) {
        Map<Long, Integer> itemQuantity = new HashMap<>();
        cookies.stream()
                .filter(cookie -> cookie.getName().startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_ID))
                .forEach(cookie -> {
                    itemQuantity.put(Long.parseLong(cookie.getValue()),
                            /*quantity=*/ Integer.parseInt(cookies.stream()
                                    .filter(cookie1 ->
                                            cookie1.getName().equals(
                                                    COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_QUANTITY + CookiesUtils.getCookieId(cookie.getName())))
                                    .findFirst().get().getValue()));
                });

        OrderDto basket = orderService.getBasket(userId);
        if (basket == null) {
            basket = orderService.createBasket(userId);
        }
        orderService.addItemsToBasket(basket.getId(), itemQuantity);
    }

    private List<Cookie> deleteBasketCookies(List<Cookie> cookies) {
        return cookies.stream()
                .filter(cookie -> cookie.getName().startsWith(COOKIE_BASKET_PREFIX)).collect(Collectors.toList());
    }

    @GetMapping("/logout")
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
