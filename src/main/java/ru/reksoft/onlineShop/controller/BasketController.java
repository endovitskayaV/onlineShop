package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.model.dto.OrderedItemDto;
import ru.reksoft.onlineShop.service.ItemService;
import ru.reksoft.onlineShop.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private OrderService orderService;
    private ItemService itemService;

    @Autowired
    public BasketController(OrderService orderService, ItemService itemService) {
        this.orderService = orderService;
        this.itemService = itemService;
    }

    @GetMapping("/add")
    public ResponseEntity add(long itemId) {
        //TODO: get user id
        long userId = 3;
        orderService.addToBasket(userId, itemId);
        //TODO: return order id and ask user whether to go to basket page or not
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public String getBasket(Model model) {
        //TODO: get user id
        long userId = 1;
        // 1- in basket
        OrderDto orderDto = orderService.getByStatusIdAndUserId(1, userId);
        List<ItemDto> items = new ArrayList<>();
        orderDto.getItems()
                .forEach(orderedItem -> items.add(itemService.getById(orderedItem.getItemId())));
        model.addAttribute("items", items);
        List<Integer> quatities = new ArrayList<>();
        orderDto.getItems().forEach(orderedItem -> quatities.add(orderedItem.getQuantity()));
        model.addAttribute("quantities", quatities);
        return "basket";
    }


    @GetMapping("/{id}/edit")
    public RedirectView edit(@PathVariable long id, OrderedItemDto orderedItemDto) {
        OrderDto orderDto = orderService.getByStatusIdAndUserId(1, 1);
        //////////////////////
        orderDto.getItems().stream()
                .filter(orderedItem -> orderedItem.equals(orderedItemDto))
                .findFirst().orElse(null);

        return new RedirectView("/basket");
    }
}
