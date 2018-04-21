package ru.reksoft.onlineShop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.reksoft.onlineShop.controller.util.ClientDataConstructor;
import ru.reksoft.onlineShop.controller.util.Error;
import ru.reksoft.onlineShop.controller.util.SortCriteria;
import ru.reksoft.onlineShop.model.dto.*;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.CharacteristicService;
import ru.reksoft.onlineShop.service.ItemService;
import ru.reksoft.onlineShop.service.StorageService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/items")
public class ItemController {
    private static final String NO_PHOTO_NAME = "no_photo.png";
    private ItemService itemService;
    private CategoryService categoryService;
    private CharacteristicService characteristicService;
    private StorageService storageService;

    @Autowired
    public ItemController(ItemService itemService,
                          CategoryService categoryService,
                          CharacteristicService characteristicService, StorageService storageService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.characteristicService = characteristicService;
        this.storageService = storageService;
    }


    private Boolean getSafeBoolean(Boolean booleanValue) {
        return booleanValue == null ? true : booleanValue;
    }

    @GetMapping("/catalogue")
    @ResponseBody
    public List<ItemDto> getAllSorted(@RequestParam(required = false) String category,
                                      @RequestParam SortCriteria sortBy,
                                      @RequestParam boolean acs) {

        if (category == null) {
            List<ItemDto> items = itemService.getAll(getSafeBoolean(acs), sortBy);
            return itemService.getAll(getSafeBoolean(acs), sortBy);
        } else {
            CategoryDto categoryDto = categoryService.getByName(category);
            List<ItemDto> items = itemService.getByCategoryId(categoryDto.getId(), getSafeBoolean(acs), sortBy);
            return itemService.getByCategoryId(categoryDto.getId(), getSafeBoolean(acs), sortBy);
        }
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("items", itemService.getAll());
        return "home";
    }

    @GetMapping(params = "category")
    public String getByCategory(Model model, String category) {
        CategoryDto categoryDto = categoryService.getByName(category);
        List<ItemDto> items = itemService.getByCategoryId(categoryDto.getId());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("items", items);
        model.addAttribute("characteristics", categoryDto.getCharacteristics());
        return "home";
    }


//    @GetMapping(params = "category")
//    public String getByCategoryAndCharacteristics(Model model,
//                                String category,
//                                @RequestParam(required = false) String sortBy,
//                                @RequestParam(required = false) Boolean asc,
//                                @RequestParam(required = false) List<CharacteristicDto> characteristics) {
//
//        CategoryDto categoryDto=categoryService.getByName(category);
//        model.addAttribute("categories", categoryService.getAll());
//        List<ItemDto> items = itemService.getByCategoryId(categoryDto.getId(), getSafeBoolean(asc), getSafeEnumValue(sortBy));
//        model.addAttribute("items", items);
//        model.addAttribute("characteristics", categoryDto.getCharacteristics());
//        return "home";
//    }


    @GetMapping(value = "/filter", params = "query")
    public String getByNameOrProducer(Model model, String query) {
        List<ItemDto> items = itemService.getByNameOrProducer(query);
        model.addAttribute("items", items);
        model.addAttribute("categories", categoryService.getAll());
        return "home_copy";
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
                .price(1)
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


//    @GetMapping("/files")
//    public String file() {
//        return "file_try";
//    }
//
//    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ModelAndView add(@ModelAttribute Try try1,
//                            @RequestParam("file") MultipartFile file) {
//        String name1 = file.getName();
//        return new ModelAndView("redirect:/items/");
//    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView add(ModelMap modelMap,
                            @Valid @ModelAttribute EditableItemDto editableItemDto,
                            BindingResult bindingResult,
                            @RequestParam("file") MultipartFile file) {

        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);
        editableItemDto.setPhotoName(file.isEmpty() ? NO_PHOTO_NAME : file.getOriginalFilename());
        checkIntegerFields(editableItemDto, errors);

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setItemModel(modelMap, editableItemDto);
            return new ModelAndView("add_item");
        } else {
            //  storageService.store(editableItemDto.getPhoto());
            long id = itemService.add(toItemDto(editableItemDto));
            if (id == -1) {
                modelMap.addAttribute("message", "Item '" + editableItemDto.getProducer() + " " + editableItemDto.getName() + "' already exists!");
                return new ModelAndView("error", modelMap);
            } else {
                if (!file.isEmpty()) {
                    storageService.store(file);
                }
                return new ModelAndView("redirect:/items/" + id);
            }
        }
    }

    private void setItemModel(ModelMap modelMap, EditableItemDto editableItemDto) {
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
            modelMap.addAttribute("item_characteristics", editableItemDto.getCharacteristics());
        }
    }

    private void checkIntegerFields(EditableItemDto editableItemDto, List<Error> errors) {
        try {
            if (Integer.parseInt(editableItemDto.getPrice()) < 0) {
                errors.add(new Error("price", "Price must be greater than 0"));
            }
        } catch (NumberFormatException e) {
            errors.add(new Error("price", "Price must be a number less than 2,147,483,647"));
        }
        try {
            Integer.parseInt(editableItemDto.getStorage());
        } catch (NumberFormatException e) {
            errors.add(new Error("storage", "Count must be a number less than 2,147,483,647"));
        }
    }


    private ItemDto toItemDto(EditableItemDto editableItemDto) {
        return ItemDto.builder()
                .id(editableItemDto.getId())
                .name(editableItemDto.getName())
                .producer(editableItemDto.getProducer())
                .description(editableItemDto.getDescription())
                .price(Integer.parseInt(editableItemDto.getPrice()))
                .storage(Integer.parseInt(editableItemDto.getStorage()))
                .categoryId(editableItemDto.getCategoryId())
                .characteristics(editableItemDto.getCharacteristics())
                .photoName(editableItemDto.getPhotoName())
                .build();
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

        if (itemDto.getPhotoName() != null) {
            if (itemDto.getPhotoName().equals(NO_PHOTO_NAME)) {
                itemDto.setPhotoName(null);
            }
        }
        model.addAttribute("item", itemDto);
        model.addAttribute("categories",
                categoryService.getAll().stream()
                        .filter(categoryDto -> (categoryDto.getId() != (itemDto.getCategoryId())))
                        .collect(Collectors.toList()));
        model.addAttribute("item_characteristics", itemDto.getCharacteristics());
        model.addAttribute("selectedCategory",
                categoryService.getById(itemDto.getCategoryId()));


        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("")
                .rating(0)
                .description("")
                .build();
        model.addAttribute("category", newCategoryDto);
        model.addAttribute("characteristics", characteristicService.getAll());
        return "edit";
    }

    @PostMapping("/edit")
    public ModelAndView edit(ModelMap modelMap,
                             @Valid @ModelAttribute EditableItemDto editableItemDto,
                             BindingResult bindingResult,
                             @RequestParam("file") MultipartFile file) {

        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);
        if (file.isEmpty() && editableItemDto.getPhotoName() == null) {
            editableItemDto.setPhotoName(NO_PHOTO_NAME);
        } else if (!file.isEmpty()) {
            editableItemDto.setPhotoName(file.getOriginalFilename());
        }
        checkIntegerFields(editableItemDto, errors);

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setItemModel(modelMap, editableItemDto);
            return new ModelAndView("edit");
        } else {
            if (!file.isEmpty()) {
                storageService.store(file);
            }
            return new ModelAndView("redirect:/items/" + itemService.edit(toItemDto(editableItemDto)));
        }
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
