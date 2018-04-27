package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.model.dto.OrderedItemDto;
import ru.reksoft.onlineShop.model.dto.UserDto;
import ru.reksoft.onlineShop.service.ItemService;
import ru.reksoft.onlineShop.service.OrderService;
import ru.reksoft.onlineShop.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private OrderService orderService;
    private ItemService itemService;
    private UserService userService;

    private static final String COOKIE_BASKET_PREFIX = "basket-";
    private static final String COOKIE_BASKET_ITEM_ID = "itemId-";
    private static final String COOKIE_BASKET_ITEM_QUANTITY = "quantity-";
    private static final String COOKIE_BASKET_ID = "basketId-";
    private static final int cookieAge = 60 * 60 * 24 * 7;
    private int itemCount;


    @Autowired
    public BasketController(OrderService orderService, ItemService itemService, UserService userService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
    }

    //TODO: check if cookies name is correct

    @PostMapping("/add")
    public ResponseEntity add(long itemId, HttpServletResponse response, HttpServletRequest request) {
        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user != null) {//authentificated user
            return (orderService.addToBasket(user.getId(), itemId)) ?
                    ResponseEntity.noContent().build() :
                    ResponseEntity.badRequest().build();
        } else { //unknown user
            createCookie(itemId, request).forEach(response::addCookie);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public String getBasket(Model model, HttpServletRequest request) {
        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        ItemDto[] items = new ItemDto[cookies.size() / 2 + 10];
        Integer[] quatities = new Integer[cookies.size() / 2 + 10];

        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user != null) {
            OrderDto basket = orderService.getBasket(user.getId());

            if (basket != null) {
                for (int i = 0; i < basket.getItems().size(); i++) {
                    OrderedItemDto item = basket.getItems().get(i);
                    quatities[i] = item.getQuantity();
                    items[i] = itemService.getById(item.getItemId());
                }
                if (basket.getItems().size() == 0) {
                    model.addAttribute("itemsSize", 0);
                }
            } else {
                basket = orderService.createBasket(user.getId());
                model.addAttribute("itemsSize", 0);
            }

            model.addAttribute("basketId", basket.getId());

        } else if (cookies.size() > 0) {
            Cookie basketCookie = cookies.stream()
                    .filter(cookie ->
                            cookie.getName().startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ID))
                    .findFirst().orElse(null);

            model.addAttribute("basketId", basketCookie != null ? basketCookie.getValue() : 0);

            cookies.forEach(cookie -> {
                if (cookie.getName().startsWith(COOKIE_BASKET_PREFIX)) {
                    if (cookie.getName().contains(COOKIE_BASKET_ITEM_ID)) {
                        items[getCookieId(cookie.getName())] = itemService.getById(Long.parseLong(cookie.getValue()));
                    } else if (cookie.getName().contains(COOKIE_BASKET_ITEM_QUANTITY)) {
                        quatities[getCookieId(cookie.getName())] = Integer.parseInt(cookie.getValue());
                    }
                }
            });
        } else {
            model.addAttribute("itemsSize", 0);
            model.addAttribute("basketId", 0);
        }


        model.addAttribute("quantities", quatities);
        model.addAttribute("items", items);
        return "basket";
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity edit(@PathVariable long id, boolean isIncrease, OrderedItemDto orderedItemDto,
                               HttpServletResponse response, HttpServletRequest request) {
        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user != null) {
            int quantity = orderService.setItemQuantityInBasket(id, orderedItemDto, isIncrease);
            return quantity == -1 ?
                    ResponseEntity.ok().build() :
                    ResponseEntity.badRequest().body(quantity);
        } else {
            if (orderService.canChangeIemQuantity(orderedItemDto, isIncrease)) {
                return ResponseEntity.ok(editCookie(isIncrease, orderedItemDto, response, request));
            } else {
                return ResponseEntity.badRequest().body(orderedItemDto.getQuantity());
            }
        }

    }

    @DeleteMapping("/delete/{basketId}/{itemId}")
    public ResponseEntity delete(@PathVariable long basketId, @PathVariable long itemId, HttpServletRequest request) {
        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user != null) {
            return orderService.deleteItem(basketId, itemId) ?
                    ResponseEntity.ok().build() :
                    ResponseEntity.badRequest().build();
        } else {
            List<Cookie> cookies = deleteCookie(itemId, request);
            return ResponseEntity.ok(cookies);
//            //last item in basket: only basketId, itemId, itemQuantity left -> delete whole basket
//            return cookies.size() == 3 ?
//                    ResponseEntity.badRequest().build() :
//                    ResponseEntity.ok(cookies);
        }
    }

    @GetMapping("/check/{id}")
    public ResponseEntity checkOrderDetails(@PathVariable long id) {
        List<ItemDto> buggedItems = orderService.checkOrderDetails(id);
        return buggedItems.size() == 0 ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().body(buggedItems);
    }

    private int getCookieId(String cookieName) {
        return Integer.parseInt(cookieName.substring(cookieName.lastIndexOf('-') + 1, cookieName.length()));

    }

    private List<Cookie> createCookie(long itemId, HttpServletRequest request) {
        List<Cookie> requestCookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        itemCount = 0;

        requestCookies.forEach(cookie -> {
            if (cookie.getName().startsWith(COOKIE_BASKET_PREFIX)) {
                if (cookie.getName().contains(COOKIE_BASKET_ITEM_ID)) {
                    itemCount++;
                }
            }
        });

        final int basketCount = 0;
        List<Cookie> cookies = new ArrayList<>();
        Cookie cookie;
        final String itemQuantity = "1";


        List<Cookie> foundCookie = requestCookies.stream()
                .filter(requestCookie ->
                        requestCookie.getName().startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_ID))
                .filter(cookie1 -> cookie1.getValue().equals(Long.toString(itemId))).collect(Collectors.toList());

        if (foundCookie.size() > 0) {
            requestCookies.stream()
                    .filter(requestCookie ->
                            requestCookie.getName().equals(
                                    COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_QUANTITY + getCookieId(foundCookie.get(0).getName())))
                    .findFirst().ifPresent(cookie1 -> {
                cookie1.setValue(Integer.toString(Integer.parseInt(cookie1.getValue()) + 1));
                cookie1.setMaxAge(cookieAge);
                cookies.add(cookie1);
            });
        } else {
            cookie = new Cookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_ID + itemCount, Long.toString(itemId));
            cookie.setMaxAge(cookieAge);
            cookies.add(cookie);

            cookie = new Cookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_QUANTITY + itemCount, itemQuantity);
            cookie.setMaxAge(cookieAge);
            cookies.add(cookie);
        }

        if (requestCookies.stream()
                .noneMatch(requestCookie ->
                        requestCookie.getName().
                                startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ID))) {

            cookie = new Cookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ID + basketCount, itemQuantity);
            cookie.setMaxAge(cookieAge);
            cookies.add(cookie);
        }
        return cookies;
    }

    private Cookie editCookie(boolean isIncrease, OrderedItemDto orderedItemDto, HttpServletResponse response, HttpServletRequest request) {
        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        List<Cookie> foundCookies = cookies.stream()
                .filter(cookie -> (cookie.getName()
                        .startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_ID) && cookie.getValue().equals(Long.toString(orderedItemDto.getItemId())))).collect(Collectors.toList());

        Cookie editedCookie = cookies.stream()
                .filter(cookie -> cookie.getName()
                        .equals(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_QUANTITY + getCookieId(foundCookies.get(0).getName())))
                .findFirst().orElse(null);

        editedCookie.setValue(Integer.toString(doOperation(isIncrease, Integer.parseInt(editedCookie.getValue()))));
        editedCookie.setMaxAge(cookieAge);
        response.addCookie(editedCookie);
        return editedCookie;
    }


    private List<Cookie> deleteCookie(long itemId, HttpServletRequest request) {
        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        Cookie foundCookie = cookies.stream()
                .filter(cookie -> (cookie.getName()
                        .startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_ID) && cookie.getValue().equals(Long.toString(itemId))))
                .findFirst().get();

        List<Cookie> deletedCookies = new ArrayList<>();
        deletedCookies.add(new Cookie(foundCookie.getName(), null));
        deletedCookies.add(new Cookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_QUANTITY + getCookieId(foundCookie.getName()), null));

        //last item in basket: only basketId, itemId, itemQuantity left
        if (cookies.stream().filter(cookie -> cookie.getName().startsWith(COOKIE_BASKET_PREFIX)).count() <= 3) {
            //delete whole basket
            deletedCookies.add(new Cookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ID + 0, null));
        }


        return deletedCookies;

    }

    private int doOperation(boolean increase, int number) {
        return increase ? ++number : --number;
    }
}
