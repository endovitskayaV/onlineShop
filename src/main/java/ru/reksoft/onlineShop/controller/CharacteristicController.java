package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.reksoft.onlineShop.domain.repository.CharacteristicRepository;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.CharacteristicService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/characteristics")
public class CharacteristicController {
    private CategoryService categoryService;

    @Autowired
    public CharacteristicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

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
