package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class MainController {

    //TODO: finish addition web tools
    //TODO: fix  bug: Could not execute build using Gradle installation 'C:\Gradle\gradle-4.6'
    //TODO: add leftover dependencies

    @Value("${shopName}")
    private String message;



    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

       model.addAttribute("shopName", message);

        return "index";
    }
}
