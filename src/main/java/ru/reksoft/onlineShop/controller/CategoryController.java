package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.NewCategoryDto;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.CharacteristicService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    private CharacteristicService characteristicService;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              CharacteristicService characteristicService) {
        this.categoryService = categoryService;
        this.characteristicService = characteristicService;
    }

    /**
     * Gets category by its id
     *
     * @param id category id
     * @return category dto found by its id
     */
    @GetMapping("/{id}")
    @ResponseBody
    public CategoryDto getCharacteristic(@PathVariable long id) {
        return categoryService.getById(id);
    }

    /**
     * Prepares new category for adding
     *
     * @return "add_category" template
     */
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

    /**
     * Inserts new category to database
     *
     * @param category new category that will be saved
     * @return ResponseEntity.ok containing new category id
     * or ResponseEntity.badRequest when category cannot be added
     */
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
