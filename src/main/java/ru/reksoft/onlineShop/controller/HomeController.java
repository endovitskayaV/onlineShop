package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @Autowired
    public HomeController() {
    }

    @GetMapping("/")
    public RedirectView home() {
        return new RedirectView("/items");
    }
}
