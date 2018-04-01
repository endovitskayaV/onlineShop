package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import ru.reksoft.onlineShop.domain.Item;
import ru.reksoft.onlineShop.domain.dto.*;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@EnableAutoConfiguration
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        //sortBy category rating
        model.addAttribute("items", itemService.getAll());

        //sortBy rating
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }


    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String getItemsByCategory(Model model,String category) {
        CategoryDto c=categoryService.getByName(category);
        List<ItemDto> items = itemService.getByCategoryId(c.getId());
        model.addAttribute("items", items);
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public String getById(Model model, @PathVariable long id) {
        ItemDto itemDto = itemService.getById(id);
        if (itemDto == null) return "error";
        model.addAttribute("item", itemDto);
        return "item";
    }

    public List<CharacteristicDto> getCharacteristic(Model model, long categoryId) {
        CategoryDto categoryDto = categoryService.getById(categoryId);
        if (categoryDto == null) return null;
        return new ArrayList<>(
                categoryDto.getCharacteristicRequiredMap().entrySet().stream()
                        .filter(Map.Entry::getValue) //select required characteristics
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                        .keySet()); //extract characteristics from Map <Characteristic, Boolean>
        // model.addAttribute("characteristics", characteristicList);
    }

    @RequestMapping(value = "/items/add", method = RequestMethod.GET)
    public String add(Model model) {
        ItemDto itemDto = ItemDto.builder()
                .id(0)
                .name("")
                .description("")
                .price(0)
                .storage(0)
        .build();
        model.addAttribute("item", itemDto);
        model.addAttribute("categories", categoryService.getAll());
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

    @RequestMapping(value = "/items/add", method = RequestMethod.POST)
    public RedirectView save(ItemDto itemDto) {
        itemService.add(itemDto);
        return new RedirectView("/items");
    }

    @RequestMapping(value = "/items/delete", method = RequestMethod.DELETE)
    public RedirectView delete(long id) {
        itemService.delete(id);
        return new RedirectView("/items");
    }
}
