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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.reksoft.onlineShop.controller.util.CookiesUtils.*;


@Controller
@RequestMapping("/basket")
public class BasketController {
    private OrderService orderService;
    private ItemService itemService;
    private UserService userService;
    private ModelConstructor modelConstructor;

    @Autowired
    public BasketController(OrderService orderService, ItemService itemService, UserService userService, ModelConstructor modelConstructor) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
        this.modelConstructor = modelConstructor;
    }

    @PostMapping("/add")
    public ResponseEntity add(long itemId, HttpServletRequest request) {

        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {//authentificated user
            return (orderService.addToBasket(user.getId(), itemId)) ?
                    ResponseEntity.ok("") :
                    ResponseEntity.badRequest().build();
        } else { //unknown user
            return orderService.canAddItemToBasket(itemId) ?
                    ResponseEntity.ok(createCookie(itemId, request)) :
                    ResponseEntity.badRequest().build();

        }
    }

    @GetMapping
    public String getBasket(Model model, HttpServletRequest request) {
        modelConstructor.setCurrentUser(model);

        List<Cookie> requestCookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        Cookie itemCookie = getBasketItemQuantityCookie(requestCookies);
        List<String> itemQuantityPairs;
        if (itemCookie != null) {
            itemQuantityPairs = Arrays.asList(itemCookie.getValue().split(PAIRS_DELIMITER));
            if (itemQuantityPairs.size() == 1 && itemQuantityPairs.get(0).equals("")) {
                itemQuantityPairs = Collections.emptyList();
            }

        } else {//no cookie for item
            itemQuantityPairs = Collections.emptyList();
        }

        List<ItemDto> items = new ArrayList<>();
        List<Integer> quatities = new ArrayList<>();

        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user != null) { //user authorized
            OrderDto basket = orderService.getBasket(user.getId());

            if (basket != null) { //build items model
                basket.getItems().forEach(item -> {
                    items.add(itemService.getById(item.getItemId()));
                    quatities.add(item.getQuantity());
                });
            } else {//no basket exists
                basket = orderService.createBasket(user.getId());
            }

            model.addAttribute("basketId", basket.getId());


        } else {  //user unauthorized
            if (itemQuantityPairs.size() > 0) { //build items model
                itemQuantityPairs.forEach(pair -> {
                    items.add(itemService.getById(Long.parseLong(pair.split(ITEM_QUANTITY_DELIMITER)[0])));
                    quatities.add(Integer.parseInt(pair.split(ITEM_QUANTITY_DELIMITER)[1]));
                });
            }

            Cookie basketIdCookie = requestCookies.stream().filter(cookie -> BASKET_ID.equals(cookie.getName())).findFirst().orElse(null);
            model.addAttribute("basketId", basketIdCookie != null ? basketIdCookie.getValue() : 0);
        }

        model.addAttribute("quantities", quatities);
        model.addAttribute("items", items);

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
        List<Cookie> cookies = new ArrayList<>();

        //whether basketId cookie exists
        if (requestCookies.stream()
                .noneMatch(requestCookie -> BASKET_ID.equals(requestCookie.getName()))) {
            cookies.add(newCookie(BASKET_ID, "1"));
        }

        Cookie itemCookie = getBasketItemQuantityCookie(requestCookies);
        if (itemCookie != null) {
            List<String> itemQuantityPairs = new ArrayList<>(Arrays.asList(itemCookie.getValue().split(PAIRS_DELIMITER)));

            String itemQuantityTargetPair = itemQuantityPairs.stream()
                    .filter(itemQuantity -> Long.parseLong(itemQuantity.split(ITEM_QUANTITY_DELIMITER)[0]) == itemId)
                    .findFirst().orElse(null);

            if (itemQuantityTargetPair == null) {//no basket cookie for item with id=itemId
                itemQuantityPairs.add(itemId + ITEM_QUANTITY_DELIMITER + "1");
                cookies.add(newCookie(ITEM, pairsToValue(itemQuantityPairs)));

            } else {//if exists increase quantity cookie
                String[] itemQuantity = itemQuantityTargetPair.split(ITEM_QUANTITY_DELIMITER);
                int quantity = (Integer.parseInt(itemQuantity[1])) + 1;
                itemQuantityPairs.set(itemQuantityPairs.indexOf(itemQuantityTargetPair), itemQuantity[0] + ITEM_QUANTITY_DELIMITER + quantity);
                cookies.add(newCookie(ITEM, pairsToValue(itemQuantityPairs)));
            }
        } else {//no basket cookie for item
            cookies.add(newCookie(itemId));
        }
        return cookies;
    }

    private Cookie editCookie(boolean isIncrease, OrderedItemDto orderedItemDto, HttpServletResponse response, HttpServletRequest request) {
        List<Cookie> requestCookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();

        Cookie itemCookie = getBasketItemQuantityCookie(requestCookies);

        if (itemCookie != null) {
            List<String> itemQuantityPairs = Arrays.asList(itemCookie.getValue().split(PAIRS_DELIMITER));

            String itemQuantityTargetPair = itemQuantityPairs.stream()
                    .filter(itemQuantity -> Long.parseLong(itemQuantity.split(ITEM_QUANTITY_DELIMITER)[0]) == orderedItemDto.getItemId())
                    .findFirst().orElse(null);

            if (itemQuantityTargetPair == null) {//no basket cookie for item with id=itemId
                //?
            } else {//if exists increase quantity cookie
                String[] itemQuantity = itemQuantityTargetPair.split(ITEM_QUANTITY_DELIMITER);
                int quantity = doOperation(isIncrease, Integer.parseInt(itemQuantity[1]));
                itemQuantityPairs.set(itemQuantityPairs.indexOf(itemQuantityTargetPair), itemQuantity[0] + ITEM_QUANTITY_DELIMITER + quantity);
                itemCookie = newCookie(ITEM, pairsToValue(itemQuantityPairs));
            }

        } else {
            //?
        }
        response.addCookie(itemCookie);
        return itemCookie;
    }

    private List<Cookie> deleteCookie(long itemId, HttpServletRequest request) {
        List<Cookie> requestCookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        List<Cookie> changedCookies = new ArrayList<>();
        Cookie itemCookie = getBasketItemQuantityCookie(requestCookies);

        if (itemCookie != null) {
            List<String> itemQuantityPairs = new ArrayList<>(Arrays.asList(itemCookie.getValue().split(PAIRS_DELIMITER)));
            itemQuantityPairs.removeIf(itemQuantity -> Long.parseLong(itemQuantity.split(ITEM_QUANTITY_DELIMITER)[0]) == itemId);

            if (itemQuantityPairs.size() != 0) { //not last item in basket is deleted
                //edit cookie item
                changedCookies.add(newCookie(ITEM, pairsToValue(itemQuantityPairs)));
            } else {
                //delete cookie item
                changedCookies.add(newCookie(ITEM, null));
                //delete cookie basket
                changedCookies.add(newCookie(BASKET_ID, null));
            }
        }
        return changedCookies;
    }

    private int doOperation(boolean increase, int number) {
        return increase ? ++number : --number;
    }

    private Cookie newCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(COOKIE_AGE);
        cookie.setPath("/");
        return cookie;
    }

    private Cookie newCookie(long itemId) {
        return newCookie(ITEM, itemId + ITEM_QUANTITY_DELIMITER + 1); //new item quantity=1
    }

    private Cookie getBasketItemQuantityCookie(List<Cookie> requestCookies) {
        return requestCookies.stream()
                .filter(requestCookie -> ITEM.equals(requestCookie.getName()))
                .findFirst().orElse(null);
    }

    private String pairsToValue(List<String> itemQuantityPairs) {
        return String.join(PAIRS_DELIMITER, itemQuantityPairs);
    }
}
