package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.reksoft.onlineShop.controller.util.ModelConstructor;
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
import java.util.*;
import java.util.stream.Collectors;

import static ru.reksoft.onlineShop.controller.util.CookiesUtils.*;


@Controller
@RequestMapping("/basket")
public class BasketController {
    private OrderService orderService;
    private ItemService itemService;
    private UserService userService;
    private ModelConstructor modelConstructor;
    private int itemCount;


    @Autowired
    public BasketController(OrderService orderService, ItemService itemService, UserService userService, ModelConstructor modelConstructor) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
        this.modelConstructor = modelConstructor;
    }

    @PostMapping("/add")
    public ResponseEntity add(long itemId, HttpServletResponse response, HttpServletRequest request) {

        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {//authentificated user
            return (orderService.addToBasket(user.getId(), itemId)) ?
                    ResponseEntity.ok("") :
                    ResponseEntity.badRequest().build();
        } else { //unknown user
            List<Cookie> cookies = createCookie(itemId, request);
          // co.forEach(response::addCookie);
            return ResponseEntity.ok(cookies);
        }
    }

    @GetMapping
    public String getBasket(Model model, HttpServletRequest request) {
        modelConstructor.setCurrentUser(model);

        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        ItemDto[] items = new ItemDto[cookies.size() / 2 + 10];
        Integer[] quatities = new Integer[cookies.size() / 2 + 10];

        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) { //user authorized
            OrderDto basket = orderService.getBasket(user.getId());

            if (basket != null) { //build items model
                for (int i = 0; i < basket.getItems().size(); i++) {
                    OrderedItemDto item = basket.getItems().get(i);
                    quatities[i] = item.getQuantity();
                    items[i] = itemService.getById(item.getItemId());
                }
            } else {
                basket = orderService.createBasket(user.getId());
            }

            model.addAttribute("basketId", basket.getId());

            //user unauthorized
        } else if (cookies.size() > 0) { //build items model
            Cookie basketCookie = cookies.stream()
                    .filter(cookie -> cookie.getName().startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ID))
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
            model.addAttribute("basketId", 0);
        }

        model.addAttribute("quantities", quatities);

        if (Arrays.stream(items).allMatch(Objects::isNull)) {
            model.addAttribute("items", Collections.EMPTY_LIST);
        } else {
            model.addAttribute("items", items);
        }

        return "cart";
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity edit(@PathVariable long id, boolean isIncrease, OrderedItemDto orderedItemDto,
                               HttpServletResponse response, HttpServletRequest request) {

        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {//user authorized
            int quantity = orderService.setItemQuantityInBasket(id, orderedItemDto, isIncrease);
            return quantity == -1 ?
                    ResponseEntity.ok("") :
                    ResponseEntity.badRequest().body(quantity);
        } else { //user unauthorized
            if (orderService.canChangeItemQuantity(orderedItemDto, isIncrease)) {
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
        }
    }


    @DeleteMapping("/delete/{basketId}")
    public ResponseEntity delete(@PathVariable long basketId) {

        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            orderService.deleteBasket(basketId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/check/{id}")
    public ResponseEntity checkOrderDetails(@PathVariable long id) {
        List<ItemDto> buggedItems = orderService.checkOrderDetails(id);
        return buggedItems.size() == 0 ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().body(buggedItems);
    }


    private List<Cookie> createCookie(long itemId, HttpServletRequest request) {

        List<Cookie> requestCookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        itemCount = 0;

        //set itemCount
        requestCookies.forEach(cookie -> {
            if (cookie.getName().startsWith(COOKIE_BASKET_PREFIX)) {
                if (cookie.getName().contains(COOKIE_BASKET_ITEM_ID)) {
                    itemCount++;
                }
            }
        });

        final int basketCount = 0;
        List<Cookie> cookies = new ArrayList<>();
        final String itemQuantity = "1";

        //find cookie for item with id=itemId
        List<Cookie> foundCookie = requestCookies.stream()
                .filter(requestCookie ->
                        requestCookie.getName().startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_ID))
                .filter(cookie1 -> cookie1.getValue().equals(Long.toString(itemId))).collect(Collectors.toList());

        if (foundCookie.size() > 0) { //if exists increase quantity cookie
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
            cookies.add(newCookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_ID + itemCount, Long.toString(itemId)));
            cookies.add(newCookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_QUANTITY + itemCount, itemQuantity));
        }

        //whether basketId cookie exists
        if (requestCookies.stream()
                .noneMatch(requestCookie ->
                        requestCookie.getName().
                                startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ID))) {

            cookies.add(newCookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ID + basketCount, itemQuantity));
        }

        return cookies;
    }

    private Cookie editCookie(boolean isIncrease, OrderedItemDto orderedItemDto, HttpServletResponse response, HttpServletRequest request) {
        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        //find cookie for item with id=orderedItemDto.getItemId()
        List<Cookie> foundCookies = cookies.stream()
                .filter(cookie -> (cookie.getName()
                        .startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_ID) && cookie.getValue().equals(Long.toString(orderedItemDto.getItemId())))).collect(Collectors.toList());

        //find cookie for quantity
        Cookie editedCookie = cookies.stream()
                .filter(cookie -> cookie.getName()
                        .equals(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_QUANTITY + getCookieId(foundCookies.get(0).getName())))
               .findFirst().orElse(null);

        editedCookie.setValue(Integer.toString(doOperation(isIncrease, Integer.parseInt(editedCookie.getValue()))));
        response.addCookie(editedCookie);
        return editedCookie;
    }


    private List<Cookie> deleteCookie(long itemId, HttpServletRequest request) {
        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        //find cookie for item with id=itemId
        Cookie foundCookie = cookies.stream()
                .filter(cookie -> (cookie.getName()
                        .startsWith(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_ID) && cookie.getValue().equals(Long.toString(itemId))))
                .findFirst().get();

        List<Cookie> deletedCookies = new ArrayList<>();
        deletedCookies.add(new Cookie(foundCookie.getName(), null)); //item cookie
        deletedCookies.add(new Cookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ITEM_QUANTITY + getCookieId(foundCookie.getName()), null)); //quantity cookie

        //if last item in basket: only basketId, itemId, itemQuantity left
        if (cookies.stream().filter(cookie -> cookie.getName().startsWith(COOKIE_BASKET_PREFIX)).count() <= 3) {
            //delete whole basket
            deletedCookies.add(new Cookie(COOKIE_BASKET_PREFIX + COOKIE_BASKET_ID + 0, null));
        }
        return deletedCookies;

    }

    private int doOperation(boolean increase, int number) {
        return increase ? ++number : --number;
    }

    private Cookie newCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(cookieAge);
        cookie.setPath("/");
        return cookie;
    }
}
