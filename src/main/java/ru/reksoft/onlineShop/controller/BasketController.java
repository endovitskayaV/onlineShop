package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.model.dto.OrderedItemDto;
import ru.reksoft.onlineShop.service.ItemService;
import ru.reksoft.onlineShop.service.OrderService;
import ru.reksoft.onlineShop.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private OrderService orderService;
    private ItemService itemService;
    private UserService userService;

    @Autowired
    public BasketController(OrderService orderService, ItemService itemService, UserService userService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity add(long itemId) {
        //  long userId = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        long userId = 1; //TODO: get user id
        return orderService.addToBasket(userId, itemId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().build();
    }

    @GetMapping
    public String getBasket(Model model) {
        long userId = 1; //TODO: get user id
        OrderDto basket = orderService.getBasket(userId);
        List<ItemDto> items = new ArrayList<>();
        List<Integer> quatities = new ArrayList<>();
        if (basket != null) {
            basket.getItems().forEach(item -> {
                quatities.add(item.getQuantity());
                items.add(itemService.getById(item.getItemId()));
            });
            model.addAttribute("basketId", basket.getId());
        }
        model.addAttribute("quantities", quatities);
        model.addAttribute("items", items);
        return "basket";
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity edit(@PathVariable long id, boolean isIncrease, OrderedItemDto orderedItemDto) {
        int quantity = orderService.setItemQuantityInBasket(id, orderedItemDto, isIncrease);
        return quantity == -1 ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().body(quantity);

    }

    @DeleteMapping("/delete/{basketId}/{itemId}")
    public ResponseEntity delete(@PathVariable long basketId, @PathVariable long itemId) {
        return orderService.deleteItem(basketId, itemId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @GetMapping("/check/{id}")
    public ResponseEntity checkOrderDetails(@PathVariable long id) {
        List<ItemDto> buggedItems = orderService.checkOrderDetails(id);
        return buggedItems.size() == 0 ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().body(buggedItems);
    }
}
