package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.EditableCharacteristic;
import ru.reksoft.onlineShop.service.CategoryService;

import java.util.HashMap;

@Controller
@RequestMapping("/categories")
public class CategoriesController {
    private CategoryService categoryService;

    @Autowired
    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("")
                .description("")
                .characteristicRequiredMap(new HashMap<>())
                .build();
        model.addAttribute("category", categoryDto);

        EditableCharacteristic editableCharacteristic = EditableCharacteristic.builder()
                .name("")
                .type("")
                .build();
        model.addAttribute("characteristic", editableCharacteristic);
        return "add_category";
    }

    @PostMapping(value = "/add", params = "category")
    public void add(CategoryDto category) {
    }
}
