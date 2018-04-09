package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.service.OrderService;

;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public String getAllByUserId(Model model) {
        model.addAttribute(orderService.getAllUserId(1));
        return "/orders";
    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable long id) {
        OrderDto orderDto = orderService.getById(id);
        if (orderDto == null) {
            model.addAttribute("message", "No such order");
            return "error";
        } else {
            model.addAttribute("order", orderDto);
            return "/order_info";
        }
    }
}
