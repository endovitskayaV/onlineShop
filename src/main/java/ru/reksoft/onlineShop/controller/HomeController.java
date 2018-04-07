package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.ItemService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    public HomeController(){
    }

    @GetMapping("/")
    public RedirectView home(Model model) {
        return new RedirectView("/items");
    }
}
