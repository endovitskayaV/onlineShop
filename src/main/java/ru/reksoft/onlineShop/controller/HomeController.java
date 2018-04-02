package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.ItemService;

@Controller
public class HomeController {
    private ItemService itemService;
    private CategoryService categoryService;

    @Autowired
    public HomeController(ItemService itemService,
                          CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }


    @GetMapping("/")
    public String home(Model model) {
        //sortBy category rating
        model.addAttribute("items", itemService.getAll());

        //sortBy rating
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }
}
