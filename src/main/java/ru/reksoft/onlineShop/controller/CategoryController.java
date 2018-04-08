package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.reksoft.onlineShop.model.dto.*;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.CharacteristicService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    private CharacteristicService characteristicService;

    @Autowired
    public CategoryController(CategoryService categoryService, CharacteristicService characteristicService) {
        this.categoryService = categoryService;
        this.characteristicService = characteristicService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CategoryDto getCharacteristic(@PathVariable long id) {
        return categoryService.getById(id);
    }

    @GetMapping("/add")
    public String add(Model model) {
        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("")
                .description("")
                .build();
        model.addAttribute("category", newCategoryDto);
        model.addAttribute("characteristics", characteristicService.getAll());
        return "add_category";
    }

    @PostMapping(value = "/add")
    public ResponseEntity add(NewCategoryDto category) {
        long id = categoryService.add(category);
        if (id == -1) {
            return ResponseEntity
                    .badRequest().body("Category '" + category.getName() + "' already exists!");
        } else {
            return ResponseEntity.ok(id);
        }
    }
}
