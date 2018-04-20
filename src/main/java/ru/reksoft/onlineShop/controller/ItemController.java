package ru.reksoft.onlineShop.controller;


import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.model.dto.EditableItemDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.model.dto.NewCategoryDto;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.service.CharacteristicService;
import ru.reksoft.onlineShop.service.ItemService;
import ru.reksoft.onlineShop.service.StorageService;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static ru.reksoft.onlineShop.service.StorageService.ROOT_LOCATION;

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

    private SortCriteria getSafeEnumValue(String value) {
        try {
            return SortCriteria.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) String sortBy,
                         @RequestParam(required = false) Boolean asc) {

        model.addAttribute("categories", categoryService.getAll());
        List<ItemDto> items;
        if (sortBy == null) {
            items = itemService.getAll();
        } else if (getSafeEnumValue(sortBy) == null) { //process incorrect sort criteria
            items = itemService.getAll(); //TODO:think of better way to process incorrect sort criteria
        } else { //&& sortBy!=null
            items = itemService.getAll(getSafeBoolean(asc), SortCriteria.valueOf(sortBy));
        }

        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping(params = "category")
    public String getByCategory(Model model,
                                String category,
                                @RequestParam(required = false) String sortBy,
                                @RequestParam(required = false) Boolean asc,
                                @RequestParam(required = false) List<CharacteristicDto> characteristics) {

        model.addAttribute("categories", categoryService.getAll());
        List<ItemDto> items;
        if (characteristics == null) {
            items = itemService.getByCategoryId((categoryService.getByName(category)).getId(), getSafeBoolean(asc), getSafeEnumValue(sortBy));
        } else {
            //TODO:set items!
            items = itemService.getAll();
        }

        model.addAttribute("items", items);
        return "home";
    }


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
        ItemDto itemDto = editableItemDtoToItemDto(editableItemDto, errors);

        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setItemModel(modelMap, editableItemDto);
            return new ModelAndView("add_item");
        } else {
            //  storageService.store(editableItemDto.getPhoto());
            long id = itemService.add(itemDto);
            if (id == -1) {
                modelMap.addAttribute("message", "Item '" + itemDto.getProducer() + " " + itemDto.getName() + "' already exists!");
                return new ModelAndView("error", modelMap);
            } else {
                if (!file.isEmpty()) {
                    storageService.store(file);
                }

////                Resource resource = new  ClassPathResource(editableItemDto.getPhotoName());
////                try {
////                    File img = resource.getFile();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//
//                ClassLoader classLoader = getClass().getClassLoader();
//                File file2 = new File(classLoader.getResource("b.png").getFile());
//                File file1 = new File(classLoader.getResource("a.png").getFile());
//
//
//                ClassPathResource classPathResource = new ClassPathResource("c.png");
//
//                InputStream inputStream = null;
//                try {
//                    inputStream = classPathResource.getInputStream();
//                    File somethingFile = File.createTempFile("test", ".png");
////                    FileUtils.copy
////                            .copyInputStreamToFile(inputStream, somethingFile);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//try {
//    byte[] bytes = file.getBytes();
//
//
//    File directory = new File("src/main/resources");
//    File imageFile = File.createTempFile("q", ".png", directory);
//    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(imageFile));
//    outputStream.write(bytes);
//    outputStream.close();
//
//}catch (Exception e){
//                    e.printStackTrace();
//}

                FileSystemResource imgFile = new FileSystemResource("src\\main\\resources\\y.png");
                try {
                   String g= imgFile.getURI().toString();
                   g+="d";
                } catch (IOException e) {
                    e.printStackTrace();
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
                .photoName(editableItemDto.getPhotoName())
                .build();
//        if (editableItemDto.getPhoto() == null) {
//           itemDto.setPhotoName(NO_PHOTO_NAME);
//        } else {
//            itemDto.setPhotoName(editableItemDto.getPhoto().getName());
//        }


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
        if (itemDto.getPhotoName().equals(NO_PHOTO_NAME)) {
            itemDto.setPhotoName(null);
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
    public ResponseEntity edit(ModelMap modelMap,
                               @Valid @ModelAttribute EditableItemDto editableItemDto,
                               BindingResult bindingResult,
                               @RequestParam("file") MultipartFile file) {

        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);
        if (file.isEmpty() && editableItemDto.getPhotoName() == null) {
        editableItemDto.setPhotoName(NO_PHOTO_NAME);
        }else if(!file.isEmpty()){
            editableItemDto.setPhotoName(file.getOriginalFilename());
        }
            ItemDto itemDto = editableItemDtoToItemDto(editableItemDto, errors);


        if (errors.size() > 0) {
            modelMap.addAttribute("errors", errors);
            setItemModel(modelMap, editableItemDto);
            return ResponseEntity.badRequest().body(errors);
        } else {
            return ResponseEntity.ok(itemService.edit(itemDto));
        }

//
//        List<Error> errors = ClientDataConstructor.getFormErrors(bindingResult);
//        editableItemDto.setPhotoName(file.isEmpty() ? NO_PHOTO_NAME : file.getOriginalFilename());
//        ItemDto itemDto = editableItemDtoToItemDto(editableItemDto, errors);
//
//        if (errors.size() > 0) {
//            modelMap.addAttribute("errors", errors);
//            setItemModel(modelMap, editableItemDto);
//            return new ModelAndView("add_item");
//        } else {
//            //  storageService.store(editableItemDto.getPhoto());
//            long id = itemService.add(itemDto);
//            if (id == -1) {
//                modelMap.addAttribute("message", "Item '" + itemDto.getProducer() + " " + itemDto.getName() + "' already exists!");
//                return new ModelAndView("error", modelMap);
//            } else {
//                if (!file.isEmpty()) {
//                    storageService.store(file);
//                }
//                return new ModelAndView("redirect:/items/" + id);
//            }
//        }
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
