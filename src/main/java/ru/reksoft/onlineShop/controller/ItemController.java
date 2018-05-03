package ru.reksoft.onlineShop.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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
import ru.reksoft.onlineShop.service.*;

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
    private ClientDataConstructor clientDataConstructor;
    private UserService userService;

    @Autowired
    public ItemController(ItemService itemService,
                          CategoryService categoryService,
                          CharacteristicService characteristicService,
                          StorageService storageService, ClientDataConstructor clientDataConstructor,
                          UserService userService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.characteristicService = characteristicService;
        this.storageService = storageService;
        this.clientDataConstructor = clientDataConstructor;
        this.userService = userService;
    }

    @GetMapping
    public String getAll(Model model, @RequestParam(required = false) String category,
                         @RequestParam(required = false) SortCriteria sortBy,
                         @RequestParam(required = false) Boolean acs) {
        List<ItemDto> items;

        if (category == null) {
            items = itemService.getAll(getSafeBoolean(acs), sortBy);
        } else {
            items = itemService.getByCategoryId(categoryService.getByName(category).getId(), getSafeBoolean(acs), sortBy);
            model.addAttribute("characteristics", itemService.getCharacteristicValues(categoryService.getByName(category).getId()));
        }

        setModel(model, items, sortBy, getSafeBoolean(acs), categoryService.getAll(), category, null);
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
                sortBy, getSafeBoolean(acs),
                categoryService.getAll(),
                category,
                setChosenCharacteristics(category, getStringListMap(characteristics)));

        return "home";
    }


    private List<CharacteristicValueDto> setChosenCharacteristics(String category, Map<String, List<String>> characteristicMap) {
        List<CharacteristicValueDto> dbcharacteristics = itemService.getCharacteristicValues(categoryService.getByName(category).getId());
        dbcharacteristics.forEach(dbcharacteristicValueDto ->
                dbcharacteristicValueDto.getValues().forEach(dbcharacteristicValue ->
                        characteristicMap.forEach((code, values) -> {
                            if (values.stream().anyMatch(dbcharacteristicValue.getValue()::equals)
                                    && dbcharacteristicValueDto.getCode().equals(code)) {
                                dbcharacteristicValue.setChecked(true);
                            }
                        })
                )
        );

        return dbcharacteristics;
    }

    @GetMapping("/find")
    public String getAllByQuery(Model model, String query,
                                @RequestParam(required = false) Long categoryId,
                                @RequestParam(required = false) SortCriteria sortBy,
                                @RequestParam(required = false) Boolean acs) {
        String category;
        List<ItemDto> items;
        if (categoryId == null) {
            category = null;
            items = itemService.getByNameOrProducer(query, getSafeBoolean(acs), sortBy);
            model.addAttribute("specifyCategory", categoryService.getByQuery(query));

        } else {
            category = categoryService.getById(categoryId).getName();
            items = itemService.getByQueryAndCategoryId(query, categoryId, getSafeBoolean(acs), sortBy);
            model.addAttribute("categoryId", categoryId);
        }

        setModel(model, items, sortBy, getSafeBoolean(acs), categoryService.getAll(), category, null);
        setSearchModel(model,query,sortBy,acs);
        return "home";
    }

    @GetMapping("/search")
    public ResponseEntity getAll(Model model, String query,
                                 @RequestParam(required = false) Long categoryId,
                                 @RequestParam(required = false) SortCriteria sortBy,
                                 @RequestParam(required = false) Boolean acs) {

        clientDataConstructor.setCurrentUser(model);

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
        clientDataConstructor.setCurrentUser(model);

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
    @Secured("ROLE_SELLER")
    @GetMapping("add")
    public String add(ModelMap model) {
        ItemDto itemDto = ItemDto.builder()
                .name("")
                .producer("")
                .description("")
                .price(1)
                .storage(0)
                .build();
        model.addAttribute("item", itemDto);

        setNewCategoryDtoModel(model, characteristicService.getAll(), categoryService.getAll());
        return "add_item";
    }

    @Secured("ROLE_SELLER")
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView add(ModelMap modelMap,
                            @Valid @ModelAttribute EditableItemDto editableItemDto,
                            BindingResult bindingResult,
                            @RequestParam("file") MultipartFile file) {

        List<Error> errors = clientDataConstructor.getFormErrors(bindingResult);

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

    /**
     * Prepares item for updating
     *
     * @param model
     * @param id    item id
     * @return "edit_item" template
     * or "error" template if item id does not exist
     */
    @Secured("ROLE_SELLER")
    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable long id) {
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
        model.addAttribute("item_characteristics", itemDto.getCharacteristics());
        model.addAttribute("selectedCategory", categoryService.getById(itemDto.getCategoryId()));
        setNewCategoryDtoModel(model,
                characteristicService.getAll(),
                /*categories*/ categoryService.getAll().stream()
                        .filter(categoryDto -> (categoryDto.getId() != (itemDto.getCategoryId())))
                        .collect(Collectors.toList()));

        return "edit";
    }

    @Secured("ROLE_SELLER")
    @PostMapping("/edit")
    public ModelAndView edit(ModelMap modelMap,
                             @Valid @ModelAttribute EditableItemDto editableItemDto,
                             BindingResult bindingResult,
                             @RequestParam("file") MultipartFile file) {

        List<Error> errors = clientDataConstructor.getFormErrors(bindingResult);

        checkIntegerFields(editableItemDto, errors);

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setItemModel(modelMap, editableItemDto);
            return new ModelAndView("edit");
        } else {
            if (file.isEmpty() && editableItemDto.getPhotoName() == null) {
                editableItemDto.setPhotoName(NO_PHOTO_NAME);
            } else if (!file.isEmpty()) {
                editableItemDto.setPhotoName(file.getOriginalFilename());
            }

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
    @Secured("ROLE_SELLER")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        if (itemService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Item cannot be deleted as it is ordered");
        }
    }

    private void setItemModel(ModelMap modelMap, EditableItemDto editableItemDto) {
        modelMap.addAttribute("item", editableItemDto);
        setNewCategoryDtoModel(modelMap, characteristicService.getAll(), categoryService.getAll());

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
        UserDto u = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
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
                .sellerId(userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId())
                .build();
    }

    private Map<String, List<String>> getStringListMap(Map<String, String> stringStringMap) {
        Map<String, List<String>> stringListMap = new HashMap<>();
        stringStringMap.forEach((key, value) ->
                stringListMap.put(key, Arrays.asList(value.split(","))));
        return stringListMap;
    }

    private Boolean getSafeBoolean(Boolean booleanValue) {
        return booleanValue == null ? false : booleanValue;
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
        clientDataConstructor.setCurrentUser(model);

        model.addAttribute("item", itemDto);
        model.addAttribute("categories", categories);
        model.addAttribute("characteristics", characteristics);
    }

    private void setModel(Model model, List<ItemDto> itemDto, SortCriteria sortBy, Boolean acs,
                          List<CategoryDto> categories, String selectedCategory, List<CharacteristicValueDto> characteristics) {

        clientDataConstructor.setCurrentUser(model);

        model.addAttribute("items", itemDto);
        model.addAttribute("categories", categories);
        if (sortBy != null) {
            model.addAttribute("selectedSortCriteria", setSelectedSortCriteria(sortBy, acs));
        }
        if (selectedCategory != null) {
            model.addAttribute("selectedCategory", selectedCategory);
        }
        if (characteristics != null) {
            model.addAttribute("characteristics", characteristics);
        }
    }

    private void setNewCategoryDtoModel(ModelMap modelMap, List<CharacteristicDto> characteristics, List<CategoryDto> categories) {
        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("")
                .rating(0)
                .description("")
                .build();
        modelMap.addAttribute("category", newCategoryDto);

        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("characteristics", characteristics);
    }


    private void setSearchModel(Model model, String query, SortCriteria sortBy, Boolean acs) {
        model.addAttribute("query", query);
        model.addAttribute("sortBy", sortBy == null ? SortCriteria.POPULARITY : sortBy);
        model.addAttribute("acs", getSafeBoolean(acs));
    }

    @Data
    @AllArgsConstructor
    private class ItemsCategories {
        List<ItemDto> items;
        Set<CategoryDto> categories;
    }
}
