package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.service.ItemService;
import ru.reksoft.onlineShop.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private ItemService itemService;

    @Autowired
    public OrderController(OrderService orderService, ItemService itemService) {
        this.orderService = orderService;
        this.itemService = itemService;
    }

    @GetMapping
    public String getAll(Model model) {
        long userId = 1; //TODO: get user id
        model.addAttribute("orders", orderService.getAllOrderedByUserId(userId));
        return "orders";
    }

    @GetMapping("/finish/{id}")
    public String finishOrder(Model model, @PathVariable long id) {
        return getOrderModel(model, id) ? "finish_order" : "error";
    }

    @PostMapping(value = "/finish")
    public ResponseEntity finishOrder(@RequestBody OrderDto orderDto) {
        orderService.finishOrder(orderDto);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("{id}")
    public String getById(Model model, @PathVariable long id) {
        return getOrderModel(model, id) ? "order_info" : "error";
    }

    private boolean getOrderModel(Model model, long id) {
        OrderDto orderDto = orderService.getById(id);

        if (orderDto == null) {
            model.addAttribute("message", "No such order");
            return false;
        }

        if (orderDto.getDeliveryAddress() == null) {
            orderDto.setDeliveryAddress("");
        }
        model.addAttribute("order", orderDto);

        List<ItemDto> items = new ArrayList<>();
        List<Integer> quatities = new ArrayList<>();

        orderDto.getItems()
                .forEach(orderedItem -> items.add(itemService.getById(orderedItem.getItemId())));
        orderDto.getItems().forEach(orderedItem -> quatities.add(orderedItem.getQuantity()));

        model.addAttribute("items", items);
        model.addAttribute("quantities", quatities);

        return true;
    }
}
