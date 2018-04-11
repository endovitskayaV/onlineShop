package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("/add")
    public ResponseEntity add(long itemId) {
        long userId = 1; //TODO: get user id
        orderService.addToBasket(userId, itemId);
        //TODO: return order id and ask user whether to go to basket page or not
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public String getBasket(Model model) {
        long userId = 1; //TODO: get user id
        OrderDto orderDto = orderService.getByStatusIdAndUserId(1, userId);  // 1- in basket
        List<ItemDto> items = new ArrayList<>();
        orderDto.getItems()
                .forEach(orderedItem -> items.add(itemService.getById(orderedItem.getItemId())));
        model.addAttribute("items", items);
        List<Integer> quatities = new ArrayList<>();
        orderDto.getItems().forEach(orderedItem -> quatities.add(orderedItem.getQuantity()));
        model.addAttribute("quantities", quatities);
        model.addAttribute("basketId", orderDto.getId());
        return "basket";
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity edit(@PathVariable long id, OrderedItemDto orderedItemDto) {
        int quantity = orderService.increaseItemQuantity(id, orderedItemDto);
        return quantity == -1 ?
                ResponseEntity.ok().build():
                ResponseEntity.badRequest().body(quantity);

    }
}
