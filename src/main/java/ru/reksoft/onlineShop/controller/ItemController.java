package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.NewItemDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.ItemService;

import java.util.List;
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
    public String getAll(Model model) {
        List<ItemDto> items = itemService.getAll();
        model.addAttribute("items", items);
        model.addAttribute("itemsSize", items.size());
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }


    @GetMapping(params = "category")
    public String getByCategory(Model model, String category) {
        List<ItemDto> items = itemService.getByCategoryId((categoryService.getByName(category)).getId());
        model.addAttribute("items", items);
        model.addAttribute("itemsSize", items.size());
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable long id) {
        ItemDto itemDto = itemService.getById(id);
        if (itemDto == null) {
            model.addAttribute("message", "No such item");
            return "error";
        } else {
            model.addAttribute("item", itemDto);
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("characteristics", itemDto.getCharacteristicList());
            return "item_info";
        }
    }

    @GetMapping("add")
    public String add(Model model) {
        NewItemDto newItemDto = NewItemDto.builder()
                .name("")
                .producer("")
                .description("")
                .price(0)
                .storage(0)
                .build();
        model.addAttribute("item", newItemDto);
        model.addAttribute("categories", categoryService.getAll());
        return "add_item";
    }

    @PostMapping("/add")
    public ModelAndView add(ModelMap modelMap, NewItemDto newItemDto) {
        long id = itemService.add(newItemDto);
        if (id == -1) {
            modelMap.addAttribute("message",
                    "Item '" + newItemDto.getProducer() + " " + newItemDto.getName() +
                            "' already exists!");
            return new ModelAndView("error", modelMap);
        } else {
            return new ModelAndView("redirect:/items/" + id);
        }

    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable long id) {
        ItemDto itemDto = itemService.getById(id);
        if (itemDto == null) return "error";
        model.addAttribute("item", itemDto);
        List<CategoryDto> categoryDtos = categoryService.getAll().stream().filter(x -> (!x.equals(itemDto.getCategory()))).collect(Collectors.toList());
        model.addAttribute("categories", categoryService.getAll().stream().filter(x -> (!x.equals(itemDto.getCategory()))).collect(Collectors.toList()));
        model.addAttribute("characteristics", itemDto.getCharacteristicList());
        model.addAttribute("selectedCategory", itemDto.getCategory());
        return "edit_ite";
    }

    @RequestMapping(value = "/delete/{id}")
    public RedirectView delete(Model model, @PathVariable long id) {
        itemService.delete(id);
        return new RedirectView("/items");
    }
}
