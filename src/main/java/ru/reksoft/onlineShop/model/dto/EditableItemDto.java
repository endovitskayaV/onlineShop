package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;


/**
 * Data transfer object for ItemEntity
 *
 * @see ItemEntity
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditableItemDto {
    private long id;

    @NotBlank(message = "Name must contain at least one not blank character")
    private String name;

    @NotBlank(message = "Producer must contain at least one not blank character")
    private String producer;

    /**
     * Quantity of items in stock
     */
    @NotNull(message = "Fill in count")
    private String storage;

    private String description;

    @NotNull(message = "Fill in price")
    private String price;

    /**
     * id of item category
     * eg: smartphone, food, health, etc
     *
     * @see CategoryDto
     */
    private long categoryId;

    /**
     * item characteristics
     * eg: color, weight, etc
     */
    @NotNull(message = "Choose category")
    @Valid
    private List<CharacteristicDto> characteristics;

  //  private MultipartFile photo;
}
