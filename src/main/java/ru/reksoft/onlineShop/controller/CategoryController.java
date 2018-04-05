package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.reksoft.onlineShop.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.model.dto.*;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.CharacteristicService;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    private CharacteristicService characteristicService;

    @Autowired
    public CategoryController(CategoryService categoryService, CharacteristicService characteristicService) {
        this.categoryService = categoryService;
        this.characteristicService=characteristicService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("")
                .description("")
                // .characteristicRequiredMap(new HashMap<>())
                .build();
        model.addAttribute("category", categoryDto);


        model.addAttribute("characteristics", characteristicService.getAll());
        return "add_category";
    }

    @PostMapping(value = "/add")
    public String add(EditableCategoryDto category) {
        categoryService.add(category);
        return "items";
       //return new RedirectView("/items");
    }
}
