package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.model.dto.EditableItemDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.ItemService;

import java.util.ArrayList;

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
    public String getAll(Model model) {
        model.addAttribute("items", itemService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }


    @GetMapping(params = "category")
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


    @GetMapping("add")
    public String add(Model model) {
        EditableItemDto editableItemDto = EditableItemDto.builder()
               // .id(0)
                .name("")
                .producer("")
                .description("")
                .price(0)
                .storage(0)
                .build();
        model.addAttribute("item", editableItemDto);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("characteristicsList", new ArrayList<CharacteristicDto>());
        return "add_item";
    }

    @RequestMapping(value = "/items/edit", method = RequestMethod.GET)
    public String edit(Model model, long id) {
        ItemDto itemDto = itemService.getById(id);
        if (itemDto == null) return "error";
        model.addAttribute("item", itemDto);
        model.addAttribute("categories", categoryService.getAll());
        return "add_item";
    }

    @PostMapping("/add")
    public RedirectView save(EditableItemDto editableItemDto) {
        return new RedirectView("/items/"+itemService.add(editableItemDto));
    }

    @RequestMapping(value = "/items/delete", method = RequestMethod.DELETE)
    public RedirectView delete(long id) {
        itemService.delete(id);
        return new RedirectView("/items");
    }
}
