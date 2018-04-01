package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import ru.reksoft.onlineShop.domain.dto.*;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.ItemService;

@Controller
@EnableAutoConfiguration
public class ItemController {
    private ItemService itemService;
    private CategoryService categoryService;
    ;

    @Autowired
    public ItemController(ItemService itemService,
                          CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @Value("${shopName}")
    private String message;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView home(Model model) {
        return new RedirectView("/items");
    }


    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String getAllItems(Model model) {
        model.addAttribute("items", itemService.getAll());
        return "home";
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public String getById(Model model, @PathVariable long id) {
//        ItemDto itemDto = itemService.getById(id);
//        if (itemDto == null) return "error";
//        model.addAttribute("item", itemDto);
        model.addAttribute("items", itemService.getAll());
        return "item_1";
    }


    @RequestMapping(value = "/items/add", method = RequestMethod.GET)
    public String add(Model model) {
        ItemDto itemDto = ItemDto.builder().build();
        model.addAttribute("item", itemDto);
        model.addAttribute("categories", categoryService.getAll());
        return "add_item";
    }

    @RequestMapping(value = "/items/add", method = RequestMethod.POST)
    public RedirectView add(ItemDto itemDto) {
        itemService.add(itemDto);
        return new RedirectView("/items");
    }
}
