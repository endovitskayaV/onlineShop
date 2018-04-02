package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.model.dto.EditableItemDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;
    private CategoryService categoryService;

    @Autowired
    public ItemController(ItemService itemService,
                          CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

//    @Value("${shopName}")
//    private String shopName;

    @GetMapping
    public String getByCategory(Model model, String category) {
        model.addAttribute("items", itemService.getByCategoryId((categoryService.getByName(category)).getId()));
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable long id) {
        ItemDto itemDto = itemService.getById(id);
        if (itemDto == null) {
            return "error";
        } else {
            model.addAttribute("item", itemDto);
            model.addAttribute("categories", categoryService.getAll());
            return "item";
        }
    }

    @GetMapping("/characteristics")
    public List<CharacteristicDto> getCharacteristic(long categoryId) {
        CategoryDto categoryDto = categoryService.getById(categoryId);
        if (categoryDto == null) {
            return null;
        }
        return new ArrayList<>(
                categoryDto.getCharacteristicRequiredMap().entrySet().stream()
                        .filter(Map.Entry::getValue) //select required characteristics
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                        .keySet()); //extract characteristics from Map <Characteristic, Boolean>


        // model.addAttribute("characteristics", characteristicList);
    }

    @GetMapping("add")
    public String add(Model model) {
        EditableItemDto editableItemDto=EditableItemDto.builder()
                .id(0)
                .name("")
                .description("")
                .price(0)
                .storage(0)
                .build();
        model.addAttribute("item", editableItemDto);
        model.addAttribute("categories", categoryService.getAll());
        return "add";
    }

    @RequestMapping(value = "/items/edit", method = RequestMethod.GET)
    public String edit(Model model, long id) {
        ItemDto itemDto = itemService.getById(id);
        if (itemDto == null) return "error";
        model.addAttribute("item", itemDto);
        model.addAttribute("categories", categoryService.getAll());
        return "add";
    }

    @PostMapping("/add")
    public RedirectView save(EditableItemDto itemDto) {
        //itemService.add(itemDto);
        return new RedirectView("/items");
    }

    @RequestMapping(value = "/items/delete", method = RequestMethod.DELETE)
    public RedirectView delete(long id) {
        itemService.delete(id);
        return new RedirectView("/items");
    }
}
