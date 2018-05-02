package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.reksoft.onlineShop.controller.util.ClientDataConstructor;
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
    private ClientDataConstructor clientDataConstructor;

    @Autowired
    public OrderController(OrderService orderService, ItemService itemService, UserService userService, ClientDataConstructor clientDataConstructor) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
        this.clientDataConstructor=clientDataConstructor;
    }

    @GetMapping
    public String getAll(Model model) {
        clientDataConstructor.setCurrentUser(model);

        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("orders", orderService.getAllOrderedByUserId(user.getId()));
        return "orders";
    }

    @GetMapping("/finish")
    public String finishOrder(Model model, RedirectAttributes redirectAttributes) {
        return setOrderModel(model,
                orderService.getBasket(userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId())) ?
                "finish_order" : "error";
    }

    @PostMapping(value = "/finish")
    public ResponseEntity finishOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(clientDataConstructor.getFormErrors(bindingResult));
        } else {
            orderService.finishOrder(orderDto);
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable long id) {
        return setOrderModel(model, orderService.getById(id)) ? "order_info" : "error";
    }

    private boolean setOrderModel(Model model, OrderDto orderDto) {
        clientDataConstructor.setCurrentUser(model);

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
