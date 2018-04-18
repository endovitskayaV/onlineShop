package ru.reksoft.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.reksoft.onlineShop.controller.util.ClientDataConstructor;
import ru.reksoft.onlineShop.controller.util.Error;
import ru.reksoft.onlineShop.model.dto.EditableItemDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.model.dto.NewCategoryDto;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.CharacteristicService;
import ru.reksoft.onlineShop.service.ItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;
    private CategoryService categoryService;
    private CharacteristicService characteristicService;

    @Autowired
    public ItemController(ItemService itemService,
                          CategoryService categoryService,
                          CharacteristicService characteristicService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.characteristicService = characteristicService;
    }

    /**
     * Gets all items from database, prepares model for template
     *
     * @param model
     * @return all items stored in database
     */
    @GetMapping
    public String getAll(Model model) {
        List<ItemDto> items = itemService.getAll();
        model.addAttribute("items", items);
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }

    /**
     * Gets items having given category, prepares model for template
     *
     * @param model
     * @return "home" template
     */
    @GetMapping(params = "category")
    public String getByCategory(Model model, String category) {
        List<ItemDto> items = itemService.getByCategoryId((categoryService.getByName(category)).getId());
        model.addAttribute("items", items);
        model.addAttribute("categories", categoryService.getAll());
        return "home";
    }

    /**
     * Gets item by its id, prepares model for template
     *
     * @param model
     * @param id    item id
     * @return "item_info" template or "error" template item not found
     */
    @GetMapping("{id}")
    public String getById(Model model, @PathVariable long id) {
        ItemDto itemDto = itemService.getById(id);
        if (itemDto == null) {
            model.addAttribute("message", "No such item");
            return "error";
        } else {
            model.addAttribute("item", itemDto);
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("characteristics", itemDto.getCharacteristics());
            return "item_info";
        }
    }

    /**
     * Prepares item for adding, model for template
     *
     * @param model
     * @return "add_item" template
     */
    @GetMapping("add")
    public String add(Model model) {
        ItemDto itemDto = ItemDto.builder()
                .name("")
                .producer("")
                .description("")
                .price(0)
                .storage(0)
                .build();
        model.addAttribute("item", itemDto);

        model.addAttribute("categories", categoryService.getAll());
        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("")
                .rating(0)
                .description("")
                .build();
        model.addAttribute("category", newCategoryDto);
        model.addAttribute("characteristics", characteristicService.getAll());
        return "add_item";
    }


    @PostMapping("/add")
    public ModelAndView add(ModelMap modelMap, @Valid EditableItemDto editableItemDto, BindingResult bindingResult) {
        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);
        ItemDto itemDto = editableItemDtoToItemDto(editableItemDto, errors);

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setItemModel(modelMap, editableItemDto);
            return new ModelAndView("add_item");
        } else {
            long id = itemService.add(itemDto);
            if (id == -1) {
                modelMap.addAttribute("message", "Item '" + itemDto.getProducer() + " " + itemDto.getName() + "' already exists!");
                return new ModelAndView("error", modelMap);
            } else {
                return new ModelAndView("redirect:/items/" + id);
            }
        }
    }

    private void setItemModel(ModelMap modelMap, EditableItemDto editableItemDto) {
//        if (itemDto.getPrice() == null) {
//            itemDto.setPrice(0);
//        }
//        if (itemDto.getStorage() == null) {
//            itemDto.setStorage(0);
//        }
        modelMap.addAttribute("item", editableItemDto);
        modelMap.addAttribute("categories", categoryService.getAll());
        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("")
                .rating(0)
                .description("")
                .build();
        modelMap.addAttribute("category", newCategoryDto);
        modelMap.addAttribute("characteristics", characteristicService.getAll());

        if (editableItemDto.getCategoryId() != 0) {
            modelMap.addAttribute("selectedCategory", categoryService.getById(editableItemDto.getCategoryId()));
            modelMap.addAttribute("characteristics", editableItemDto.getCharacteristics());
        }
    }

    private ItemDto editableItemDtoToItemDto(EditableItemDto editableItemDto, List<Error> errors) {
        ItemDto itemDto = ItemDto.builder()
                .id(editableItemDto.getId())
                .name(editableItemDto.getName())
                .producer(editableItemDto.getProducer())
                .description(editableItemDto.getDescription())
                .categoryId(editableItemDto.getCategoryId())
                .characteristics(editableItemDto.getCharacteristics())
                .build();
        try {
            itemDto.setPrice(Integer.parseInt(editableItemDto.getPrice()));
            if (itemDto.getPrice() < 0) {
                errors.add(new Error("price", "Price must be greater than 0"));
            }
        } catch (NumberFormatException e) {
            errors.add(new Error("price", "Price must be a number less than 2,147,483,647"));
        }
        try {
            itemDto.setStorage(Integer.parseInt(editableItemDto.getStorage()));
            if (itemDto.getStorage() < 1) {
                errors.add(new Error("storage", "Count must be greater than 1"));
            }
        } catch (NumberFormatException e) {
            errors.add(new Error("storage", "Count must be anumber less than 2,147,483,647"));
        }

        return itemDto;
    }

    /**
     * Prepares item for updating
     *
     * @param model
     * @param id    item id
     * @return "edit_item" template
     * or "error" template if item id does not exist
     */
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        ItemDto itemDto = itemService.getById(id);
        if (itemDto == null) {
            return "error";
        }

        model.addAttribute("item", itemDto);
        model.addAttribute("categories",
                categoryService.getAll().stream()
                        .filter(categoryDto -> (categoryDto.getId() != (itemDto.getCategoryId())))
                        .collect(Collectors.toList()));
        model.addAttribute("characteristics", itemDto.getCharacteristics());
        model.addAttribute("selectedCategory",
                categoryService.getById(itemDto.getCategoryId()));

        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("")
                .rating(0)
                .description("")
                .build();
        model.addAttribute("category", newCategoryDto);
        model.addAttribute("characteristics", characteristicService.getAll());
        return "edit_ite";
    }

    /**
     * Updates item
     *
     * @param itemDto item that will be edited
     * @return redirects to /items/{item_id}
     */
    @PostMapping("/edit")
    public ModelAndView edit(ItemDto itemDto) {
        long id = itemService.save(itemDto);
        return new ModelAndView("redirect:/items/" + id);

    }

    /**
     * Deletes item by its id
     *
     * @param id item id
     * @return ResponseEntity.noContent()
     * or ResponseEntity.badRequest() if item cannot be added
     * @see ResponseEntity
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        if (itemService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Item cannot be deleted as it is ordered");
        }
    }
}
