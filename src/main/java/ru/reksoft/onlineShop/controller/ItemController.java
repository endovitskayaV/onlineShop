package ru.reksoft.onlineShop.controller;


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
import ru.reksoft.onlineShop.controller.util.Error;
import ru.reksoft.onlineShop.controller.util.ModelConstructor;
import ru.reksoft.onlineShop.controller.util.SortCriteria;
import ru.reksoft.onlineShop.model.dto.*;
import ru.reksoft.onlineShop.service.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static ru.reksoft.onlineShop.storage.StorageUtils.*;

@Controller
@RequestMapping("/items")
public class ItemController {
    private static final String NO_PHOTO_NAME = "no_photo.png";
    private static final String NO_PHOTO_NAME_COMPRESSED = "no_photo_compressed.jpg";
    private ItemService itemService;
    private CategoryService categoryService;
    private CharacteristicService characteristicService;
    private StorageService storageService;
    private ModelConstructor modelConstructor;
    private UserService userService;

    @Autowired
    public ItemController(ItemService itemService, CategoryService categoryService,
                          CharacteristicService characteristicService, StorageService storageService,
                          ModelConstructor modelConstructor, UserService userService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.characteristicService = characteristicService;
        this.storageService = storageService;
        this.modelConstructor = modelConstructor;
        this.userService = userService;
    }

    @GetMapping
    public String getAll(Model model, @RequestParam(required = false) String category,
                         @RequestParam(required = false) SortCriteria sortBy,
                         @RequestParam(required = false) Boolean acs) {
        List<ItemDto> items;

        if (category == null) {
            items = itemService.getAll(getSafeBoolean(acs), sortBy);
            setModel(model, items, sortBy, getSafeBoolean(acs), categoryService.getAll(), null, null);
        } else {
            items = itemService.getByCategoryId(categoryService.getByName(category).getId(), getSafeBoolean(acs), sortBy);
            setModel(model, items, sortBy, getSafeBoolean(acs), categoryService.getAll(), category, itemService.getCharacteristicValuesByCategoryId(categoryService.getByName(category).getId()));
        }


        return "home";
    }

    @GetMapping("/filter")
    public String getByCharacteristics(Model model, @RequestParam String category,
                                       @RequestParam Map<String, String> characteristics,
                                       @RequestParam(required = false) SortCriteria sortBy,
                                       @RequestParam(required = false) Boolean acs) {

        deleteCharacteristics(characteristics, sortBy, acs);

        if (characteristics.size() > 0) {
            setModel(model,
                    itemService.getByCharacteristic(categoryService.getByName(category).getId(), getStringListMap(characteristics), getSafeBoolean(acs), sortBy),
                    sortBy, getSafeBoolean(acs),
                    categoryService.getAll(),
                    category,
                    setChosenCharacteristics(category, getStringListMap(characteristics)));
        } else {
            setModel(model,
                    itemService.getByCategoryId(categoryService.getByName(category).getId(), acs, sortBy),
                    sortBy, getSafeBoolean(acs), categoryService.getAll(), category,
                    itemService.getCharacteristicValuesByCategoryId(categoryService.getByName(category).getId()));
        }

        return "home";
    }

    @GetMapping("/find")
    public String getAllByQuery(Model model, String query,
                                @RequestParam(required = false) Long categoryId,
                                @RequestParam(required = false) Map<String, String> characteristics,
                                @RequestParam(required = false) SortCriteria sortBy,
                                @RequestParam(required = false) Boolean acs) {
        String category;
        List<ItemDto> items;

        if (categoryId == null) {
            items = itemService.getByNameOrProducer(query, getSafeBoolean(acs), sortBy);
            model.addAttribute("specifyCategory", categoryService.getByQuery(query));
            setModel(model, items, sortBy, getSafeBoolean(acs), new ArrayList<>(categoryService.getByQuery(query)), null, null);

        } else {
            category = categoryService.getById(categoryId).getName();
            model.addAttribute("categoryId", categoryId);

            deleteCharacteristics(characteristics, sortBy, acs);

            if (characteristics != null && characteristics.size() > 0) {
                setModel(model,
                        itemService.getByCharacteristicAndQuery(getStringListMap(characteristics), getSafeBoolean(acs), sortBy, categoryId, query),
                        sortBy, getSafeBoolean(acs),
                        categoryService.getAll(),
                        category,
                        setChosenCharacteristics(category, getStringListMap(characteristics)));
            } else {
                items = itemService.getByQueryAndCategoryId(query, categoryId, getSafeBoolean(acs), sortBy);
                setModel(model, items, sortBy, getSafeBoolean(acs), categoryService.getAll(), category,
                        itemService.getCharacteristicValuesByCategoryIdAndQuery(items));
            }
        }

        setSearchModel(model, query, sortBy, acs);
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
        modelConstructor.setCurrentUser(model);

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
    public ModelAndView add(ModelMap modelMap, @Valid @ModelAttribute EditableItemDto editableItemDto,
                            BindingResult bindingResult, @RequestParam("file") MultipartFile file) {

        List<Error> errors = modelConstructor.getFormErrors(bindingResult);

        editableItemDto.setPhotoNameOriginal(file.isEmpty() ? NO_PHOTO_NAME : file.getOriginalFilename());
        checkIntegerFields(editableItemDto, errors);
        validFile(file, errors);

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setItemModel(modelMap, editableItemDto);
            return new ModelAndView("add_item");
        } else {
            if (!file.isEmpty()) {
                editableItemDto.setPhotoNameOriginal(storageService.store(file));
                editableItemDto.setPhotoNameCompressed(storageService.getCompressedImage(editableItemDto.getPhotoNameOriginal()));
            }

            long id = itemService.add(toItemDto(editableItemDto));
            if (id == -1) {
                modelMap.addAttribute("message", "Item '" + editableItemDto.getProducer() + " " + editableItemDto.getName() + "' already exists!");
                return new ModelAndView("error", modelMap);
            } else {
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

        if (itemDto.getPhotoNameOriginal() != null) {
            if (itemDto.getPhotoNameOriginal().equals(NO_PHOTO_NAME)) {
                itemDto.setPhotoNameOriginal(null);
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

        List<Error> errors = modelConstructor.getFormErrors(bindingResult);
        checkIntegerFields(editableItemDto, errors);
        validFile(file, errors);

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setItemModel(modelMap, editableItemDto);
            return new ModelAndView("edit");
        } else {
            if (file.isEmpty() && editableItemDto.getPhotoNameOriginal() == null) {
                editableItemDto.setPhotoNameOriginal(NO_PHOTO_NAME);
                editableItemDto.setPhotoNameCompressed(NO_PHOTO_NAME_COMPRESSED);
            } else if (!file.isEmpty()) {
                editableItemDto.setPhotoNameOriginal(storageService.store(file));
                editableItemDto.setPhotoNameCompressed(storageService.getCompressedImage(editableItemDto.getPhotoNameOriginal()));
            } else { //file.isEmpty() && editableItemDto.getPhotoNameOriginal() != null
                String originalPhotoName = editableItemDto.getPhotoNameOriginal();
                editableItemDto.setPhotoNameCompressed(
                        getOriginalFileName(originalPhotoName) + COMPRESSED_IMAGE_POSTfIX + "." + getFileExtension(originalPhotoName));
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
            return ResponseEntity.badRequest().body("Item cannot be deleted");
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

        return ItemDto.builder()
                .id(editableItemDto.getId())
                .name(editableItemDto.getName())
                .producer(editableItemDto.getProducer())
                .description(editableItemDto.getDescription())
                .price(Integer.parseInt(editableItemDto.getPrice()))
                .storage(Integer.parseInt(editableItemDto.getStorage()))
                .categoryId(editableItemDto.getCategoryId())
                .characteristics(editableItemDto.getCharacteristics())
                .photoNameOriginal(editableItemDto.getPhotoNameOriginal())
                .photoNameCompressed(editableItemDto.getPhotoNameCompressed())
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
        modelConstructor.setCurrentUser(model);

        model.addAttribute("item", itemDto);
        model.addAttribute("categories", categories);
        model.addAttribute("characteristics", characteristics);
    }

    private void setModel(Model model, List<ItemDto> itemDto, SortCriteria sortBy, Boolean acs,
                          List<CategoryDto> categories, String selectedCategory, List<CharacteristicValueDto> characteristics) {

        modelConstructor.setCurrentUser(model);

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
        model.addAttribute("search", "search");
    }

    private List<CharacteristicValueDto> setChosenCharacteristics(String category, Map<String, List<String>> characteristicMap) {
        List<CharacteristicValueDto> dbCharacteristics = itemService.getCharacteristicValuesByCategoryId(categoryService.getByName(category).getId());

        dbCharacteristics.forEach(dbCharacteristicValueDto ->

                dbCharacteristicValueDto.getValues().forEach(dbCharacteristicValue ->
                        characteristicMap.forEach((code, values) -> {
                            if (values.stream().anyMatch(dbCharacteristicValue.getValue()::equals)
                                    && dbCharacteristicValueDto.getCode().equals(code)) {

                                dbCharacteristicValue.setChecked(true);
                            }
                        })
                )
        );

        return dbCharacteristics;
    }

    private void deleteCharacteristics(Map<String, String> characteristics, SortCriteria sortBy, Boolean acs) {
        if (characteristics != null) {

            characteristics.remove("category");
            characteristics.remove("query");
            characteristics.remove("categoryId");

            if (sortBy != null) {
                characteristics.remove("sortBy");
            }
            if (acs != null) {
                characteristics.remove("acs");
            }
        }
    }

    private void validFile(MultipartFile file, List<Error> errors) {
        if (!file.getOriginalFilename().isEmpty()) {
            if (file.getSize() > 1048575) {
                errors.add(new Error("file", "File size must be not more than 1 Mb"));
            }
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
            if (!(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))) {
                errors.add(new Error("file", "Please select a valid image file (JPEG/JPG/PNG)"));
            }
        }
    }
}
