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
        OrderDto basket = orderService.getBasket(userId);
        if(basket!=null) {
            List<ItemDto> items = new ArrayList<>();
            basket.getItems()
                    .forEach(orderedItem -> items.add(itemService.getById(orderedItem.getItemId())));
            model.addAttribute("items", items);
            List<Integer> quatities = new ArrayList<>();
            basket.getItems().forEach(orderedItem -> quatities.add(orderedItem.getQuantity()));
            model.addAttribute("quantities", quatities);
            model.addAttribute("basketId", basket.getId());
        }else{
            model.addAttribute("items", new ArrayList<>());
        }
        return "basket";
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity edit(@PathVariable long id, OrderedItemDto orderedItemDto) {
        int quantity = orderService.increaseItemQuantity(id, orderedItemDto);
        return quantity == 1 ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().body(quantity);

    }

    @DeleteMapping("/delete/{basketId}/{itemId}")
    public ResponseEntity delete(@PathVariable long basketId, @PathVariable long itemId) {
       return orderService.deleteItem(basketId, itemId)?
               ResponseEntity.ok().build():
               ResponseEntity.badRequest().build();
    }
}
