//package ru.reksoft.onlineShop.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import ru.reksoft.onlineShop.model.dto.ItemDto;
//import ru.reksoft.onlineShop.model.dto.OrderDto;
//import ru.reksoft.onlineShop.model.dto.UserDto;
//import ru.reksoft.onlineShop.service.ItemService;
//import ru.reksoft.onlineShop.service.OrderService;
//import ru.reksoft.onlineShop.service.StatusService;
//import ru.reksoft.onlineShop.service.UserService;
//
//import java.converter.ArrayList;
//import java.converter.Date;
//import java.converter.List;
//
//
//@Controller
//@EnableAutoConfiguration
//public class OrderController {
//    private OrderService orderService;
//    private UserService userService;
//    private StatusService statusService;
//    private ItemService itemService;
//
//    @Autowired
//    public OrderController(OrderService orderService,
//                           UserService userService,
//                           StatusService statusService,
//                           ItemService itemService) {
//        this.orderService = orderService;
//        this.userService = userService;
//        this.statusService = statusService;
//        this.itemService = itemService;
//    }
//
//
////    @RequestMapping(value = "/busket", method = RequestMethod.GET)
////    public String addItemToBusket(Model model, long itemId) {
////        long userId = 1; //TODO: remove it
////        if (orderService.getBusket(userId) == null) {
////
////            UserDto userDto = userService.getById(userId);
////            String deliveryAdddress = userDto.getAddress();
////            if (deliveryAdddress == null) deliveryAdddress = "";
////            List<ItemDto> itemDtoList = new ArrayList<>();
////            itemDtoList.add(itemService.getById(itemId));
////
////            orderService.save(OrderDto.builder()
////                    .user(userDto)
////                    .status(statusService.getById(0))
////                    .date(new Date())
////                    .deliveryAddress(deliveryAdddress)
////                    .itemList(itemDtoList)
////                    .build());
////        } else {
////
////        }
////        return getBusket(model);
////    }
//
////
////    @RequestMapping(value = "/busket", method = RequestMethod.GET)
////    public String getBusket(Model model) {
//////        long userId = 1; //TODO: remove it
////////        OrderDto orderDto = orderService.getBusket(userId);
//////        if (orderDto == null) model.addAttribute("empty_busket", true);
//////        else {
//////            model.addAttribute("empty_busket", false);
//////            model.addAttribute("order", orderDto);
//////        }
//////        return "busket";
// //   }
//
//
//    @RequestMapping(value = "/order", method = RequestMethod.GET)
//    public String createOrder(Model model, List<ItemDto> itemList) {
//        long userId = 1; //TODO: remove it
//        UserDto userDto = userService.getById(userId);
//        String deliveryAdddress = userDto.getAddress();
//        if (deliveryAdddress == null) deliveryAdddress = "";
//
//        orderService.save(OrderDto.builder()
//                .user(userDto)
//                .status(statusService.getById(0))
//                .date(new Date())
//                .deliveryAddress(deliveryAdddress)
//               // .itemQuantityMap()
//                .build());
//        return "/order";
//    }
//}
