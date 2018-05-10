package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.reksoft.onlineShop.controller.util.ModelConstructor;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.model.dto.UserDto;
import ru.reksoft.onlineShop.service.ItemService;
import ru.reksoft.onlineShop.service.OrderService;
import ru.reksoft.onlineShop.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private ItemService itemService;
    private UserService userService;
    private ModelConstructor modelConstructor;

    @Autowired
    public OrderController(OrderService orderService, ItemService itemService, UserService userService, ModelConstructor modelConstructor) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
        this.modelConstructor = modelConstructor;
    }

    @GetMapping
    public String getAll(Model model) {
        modelConstructor.setCurrentUser(model);

        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("orders", orderService.getAllOrderedByUserId(user.getId()));
        return "orders";
    }

    @GetMapping("/finish")
    public String finishOrder(Model model) {
        return canSetOrderModel(model,
                orderService.getBasket(userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId())) ?
                "finish_order" : "error";
    }

    @PostMapping(value = "/finish")
    public ResponseEntity finishOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(modelConstructor.getFormErrors(bindingResult));
        } else {
            orderService.finishOrder(orderDto);
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable long id) {
        return canSetOrderModel(model, orderService.getById(id)) ? "order_info" : "error";
    }

    private boolean canSetOrderModel(Model model, OrderDto orderDto) {
        modelConstructor.setCurrentUser(model);

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
        orderDto.getItems().forEach(orderedItem -> items.add(itemService.getById(orderedItem.getItemId())));
        orderDto.getItems().forEach(orderedItem -> quatities.add(orderedItem.getQuantity()));
        model.addAttribute("items", items);
        model.addAttribute("quantities", quatities);

        return true;
    }
}
