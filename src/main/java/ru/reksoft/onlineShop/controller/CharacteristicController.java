package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/characteristics")
public class CharacteristicController {
    private CategoryService categoryService;

    @Autowired
    public CharacteristicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Gets all characteristics having given category
     *
     * @param categoryId category id
     * @return characteristic dtos having given category
     */
    @GetMapping
    @ResponseBody
    public List<CharacteristicDto> getCharacteristic(long categoryId) {
        CategoryDto categoryDto = categoryService.getById(categoryId);
        if (categoryDto == null) {
            return null;
        } else {
            return categoryDto.getCharacteristics();
        }
    }
}
