package ru.reksoft.onlineShop.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
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
import java.util.*;
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

    @GetMapping
    public String getAll(Model model, @RequestParam(required = false) String category,
                         @RequestParam(required = false) SortCriteria sortBy,
                         @RequestParam(required = false) Boolean acs) {
        List<ItemDto> items;
        if (category == null) {
            items = itemService.getAll(getSafeBoolean(acs), sortBy);
        } else {
            items = itemService.getByCategoryId(categoryService.getByName(category).getId(), acs, sortBy);
            model.addAttribute("characteristicValues",
                    itemService.getCharacteristicByCategoryId(categoryService.getByName(category).getId(), acs, sortBy));
        }
        setModel(model, items, sortBy, acs, categoryService.getAll());

        if (category != null) model.addAttribute("selectedCategory", category);
        return "home";
    }

    @GetMapping("/filter")
    public String getByCharacteristics(Model model, @RequestParam String category,
                                       @RequestParam Map<String, String> characteristics,
                                       @RequestParam(required = false) SortCriteria sortBy,
                                       @RequestParam(required = false) Boolean acs) {
        characteristics.remove("category");
        if (sortBy != null) {
            characteristics.remove("sortBy");
        }
        if (acs != null) {
            characteristics.remove("acs");
        }
        setModel(model,
                itemService.getByCharacteristic(categoryService.getByName(category).getId(), getStringListMap(characteristics), getSafeBoolean(acs), sortBy),
                sortBy, acs,
                categoryService.getAll());
        if (characteristics.size() == 0) {
            model.addAttribute("characteristics", toCharacteristicValueDtoList(categoryService.getByName(category).getCharacteristics()));
        } else {
            model.addAttribute("chosenCharacteristics", characteristics);
        }
        //  List<ItemDto> items = itemService.getByCharacteristic(categoryService.getByName(category).getId(), characteristics, getSafeBoolean(acs), sortBy);
        return "home";
    }

    private Map<String, List<String>> getStringListMap(Map<String, String> stringStringMap) {
        Map<String, List<String>> stringListMap = new HashMap<>();
        stringStringMap.forEach((key, value) ->
                stringListMap.put(key, Arrays.asList(value.split(","))));
        return stringListMap;
    }

    @GetMapping("/search")
    public ResponseEntity getAll(String query,
                                 @RequestParam(required = false) Long categoryId,
                                 @RequestParam(required = false) SortCriteria sortBy,
                                 @RequestParam(required = false) Boolean acs) {
        return categoryId == null ?
                ResponseEntity.ok(new ItemsCategories(itemService.getByNameOrProducer(query, getSafeBoolean(acs), sortBy), categoryService.getByQuery(query))) :
                ResponseEntity.ok(itemService.getByQueryAndCategoryId(query, categoryId, getSafeBoolean(acs), sortBy));
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
            setModel(model, itemDto, categoryService.getAll(), itemDto.getCharacteristics());
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

    private Boolean getSafeBoolean(Boolean booleanValue) {
        return booleanValue == null ? true : booleanValue;
    }

    private String setSelectedSortCriteria(SortCriteria sortBy, Boolean acs) {
        String selectedSortCriteria = sortBy.name().toLowerCase();
        if (acs == null) {
            selectedSortCriteria += "Acs";
        } else {
            selectedSortCriteria += acs ? "Acs" : "Des";
        }
        return selectedSortCriteria;
    }

    private void setModel(Model model, ItemDto itemDto,
                          List<CategoryDto> categories, List<CharacteristicDto> characteristics) {
        model.addAttribute("item", itemDto);
        model.addAttribute("categories", categories);
        model.addAttribute("characteristics", characteristics);
    }

    private void setModel(Model model, List<ItemDto> itemDto, SortCriteria sortBy, Boolean acs,
                          List<CategoryDto> categories) {
        model.addAttribute("items", itemDto);
        model.addAttribute("categories", categories);
        if (sortBy != null) {
            model.addAttribute("selectedSortCriteria", setSelectedSortCriteria(sortBy, acs));
        }
    }

    @Data
    @AllArgsConstructor
    private class ItemsCategories {
        List<ItemDto> items;
        Set<CategoryDto> categories;
    }

    private List<CharacteristicValueDto> toCharacteristicValueDtoList(List<CharacteristicDto> characteristicDtos) {
        List<CharacteristicValueDto> characteristicValueDtos = new ArrayList<>();
        characteristicDtos.forEach(characteristicDto -> {
            if (characteristicValueDtos.stream()
                    .anyMatch(characteristicValueDto ->
                            characteristicValueDto.getCode().equals(characteristicDto.getCode()))) {
                characteristicValueDtos.get(characteristicValueDtos.indexOf((characteristicValueDtos.stream()
                        .filter(characteristicValueDto -> characteristicValueDto
                                .getCode().equals(characteristicDto.getCode()))
                        .findFirst().get()))).getValues().add(characteristicDto.getValue());
            } else {
                List<String> values = new ArrayList<>();
                values.add(characteristicDto.getValue());
                characteristicValueDtos
                        .add(new CharacteristicValueDto
                                (characteristicDto.getName(), characteristicDto.getCode(), values));
            }
        });
        return characteristicValueDtos;
    }
}
