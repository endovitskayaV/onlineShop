package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.service.CategoryService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/characteristics")
public class CharacteristicsController {
    private CategoryService categoryService;

    @Autowired
    public CharacteristicsController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseBody
    public Set<CharacteristicDto> getCharacteristic(long categoryId) {
        CategoryDto categoryDto = categoryService.getById(categoryId);
        if (categoryDto == null) {
            return null;
        }
        Set<CharacteristicDto> categoryDtos =
                categoryDto.getCharacteristicRequiredMap().entrySet().stream()
                        .filter(Map.Entry::getValue) //select required characteristics
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                        .keySet(); //extract characteristics from Map <Characteristic, Boolean>

        return categoryDtos;
    }


}
