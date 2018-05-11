package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.reksoft.onlineShop.controller.util.Error;
import ru.reksoft.onlineShop.controller.util.ModelConstructor;
import ru.reksoft.onlineShop.model.dto.LoginUserDto;
import ru.reksoft.onlineShop.model.dto.SignupUserDto;
import ru.reksoft.onlineShop.service.AuthentificationService;
import ru.reksoft.onlineShop.service.OrderService;
import ru.reksoft.onlineShop.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.reksoft.onlineShop.controller.util.CookiesUtils.*;

@Controller
public class AuthentificationController {

    private static final String DEFAULT_DESTINATION = "/items";
    private static final int CUSTOMER_ROLE_ID = 2;

    private AuthentificationService authentificationService;
    private UserService userService;
    private OrderService orderService;
    private ModelConstructor modelConstructor;

    @Autowired
    public AuthentificationController(AuthentificationService authentificationService, UserService userService,
                                      OrderService orderService, ModelConstructor modelConstructor) {
        this.authentificationService=authentificationService;
        this.userService = userService;
        this.orderService = orderService;
        this.modelConstructor = modelConstructor;
    }

    @GetMapping("/login")
    public String login(ModelMap model, @RequestParam(required = false) String destination, HttpServletRequest request) throws UnsupportedEncodingException {

        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        model.addAttribute("cookies", cookies);

        setLoginUserModel(model, LoginUserDto.builder().email("").password("").build(), java.net.URLEncoder.encode(destination, "UTF-8"));

        return "login";
    }

    @PostMapping("/login")
    public String login(ModelMap modelMap, @Valid LoginUserDto loginUserDto,
                        BindingResult bindingResult, @RequestParam String destination,
                        HttpServletRequest request, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {

        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        List<Error> errors = modelConstructor.getFormErrors(bindingResult);
        if (errors.size() == 0) {
            if (!authentificationService.login(loginUserDto.getEmail(), loginUserDto.getPassword())) {
                errors.add(new Error("form", "Wrong email or password"));
            }
        }

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setLoginUserModel(modelMap, loginUserDto, java.net.URLEncoder.encode(destination, "UTF-8"));
            return "login";
        } else {
            makeBasketFromCookies(userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId(), cookies);
            deleteBasketCookies(cookies);
            redirectAttributes.addFlashAttribute("cookies", deleteBasketCookies(cookies));
            return "redirect:" + destination;
        }
    }

    @GetMapping("/signup")
    public String signup(ModelMap model, @RequestParam String destination, HttpServletRequest request) throws UnsupportedEncodingException {

        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        model.addAttribute("cookies", cookies);

        setSignupUserModel(model,
                SignupUserDto.builder().email("").password("").confirmPassword("").roleId(CUSTOMER_ROLE_ID).build(),
                java.net.URLEncoder.encode(destination, "UTF-8"));
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(ModelMap modelMap, @Valid SignupUserDto signupUserDto,
                         BindingResult bindingResult, @RequestParam String destination,
                         HttpServletRequest request, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {

        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        List<Error> errors = modelConstructor.getFormErrors(bindingResult);

        if (errors.size() == 0) {
            if (!userService.add(signupUserDto)) {
                errors.add(new Error("form", "User with such email already exists"));
            }
        }

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setSignupUserModel(modelMap, signupUserDto, java.net.URLEncoder.encode(destination, "UTF-8"));
            return "signup";
        } else {
            authentificationService.login(signupUserDto.getEmail(), signupUserDto.getPassword());
            makeBasketFromCookies(
                    userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId(),
                    cookies);

            deleteBasketCookies(cookies);
            redirectAttributes.addFlashAttribute("cookies", deleteBasketCookies(cookies));
            return "redirect:" +  destination;
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

    private void makeBasketFromCookies(long userId, List<Cookie> requestCookies) {
        Map<Long, Integer> itemQuantity = new HashMap<>();

        Cookie itemCookie = getItemQuantityCookie(requestCookies);
        List<String> itemQuantityPairs;
        if (itemCookie != null) {
            itemQuantityPairs = Arrays.asList(itemCookie.getValue().split(PAIRS_DELIMITER));
            if (itemQuantityPairs.size() > 0) { //build items model
                itemQuantityPairs.forEach(pair -> {
                    long itemId = Long.parseLong(pair.split(ITEM_QUANTITY_DELIMITER)[0]);
                    int quantity = Integer.parseInt(pair.split(ITEM_QUANTITY_DELIMITER)[1]);
                   itemQuantity.put(itemId,quantity);
                });
            }
        }
        orderService.addItemsToBasket(userId, itemQuantity);
    }

    private List<Cookie> deleteBasketCookies(List<Cookie> cookies) {
        return cookies.stream()
                .filter(cookie -> cookie.getName().startsWith(COOKIE_BASKET_PREFIX)).collect(Collectors.toList());
    }
}
