package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        this.itemService=itemService;
    }

    @PostMapping("/add")
    public RedirectView create(ItemDto itemDto){
        orderService.createBasket(itemDto);
        return new RedirectView("/basket");
    }

    @GetMapping
    public String getByUserId(Model model){
        // 1- in basket
        OrderDto orderDto=orderService.getByStatusIdAndUserId(1,1);

        List<ItemDto> items=new ArrayList<>();
        orderDto.getItems()
                .forEach(orderedItem->items.add(itemService.getById(orderedItem.getItemId())));
        model.addAttribute("items",items);

        List<Integer> quatities=new ArrayList<>();
        orderDto.getItems().forEach(orderedItem->quatities.add(orderedItem.getQuantity()));
        model.addAttribute("quantities",quatities);
        return "basket";
    }


    @GetMapping("/{id}/edit")
    public RedirectView edit(@PathVariable long id, OrderedItemDto orderedItemDto){
       OrderDto orderDto= orderService.getByStatusIdAndUserId(1,1);
       //////////////////////
        orderDto.getItems().stream()
                .filter(orderedItem->orderedItem.equals(orderedItemDto))
               .findFirst().orElse(null);

            return new RedirectView("/basket");
    }
}
