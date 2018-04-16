package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

//import ru.reksoft.onlineShop.validator.ValidCharacteristic;

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
public class ItemDto {
    private long id;

    @NotBlank(message = "Name must contain at least one not blank character")
    private String name;

    @NotBlank(message = "Producer must contain at least one not blank character")
    private String producer;

    /**
     * Quantity of items in stock
     */
    @NotNull(message = "Fill in count")
    @Min(value = 0, message = "Count must be greater than or equal to 0")
    private Integer storage;

    private String description;

    @NotNull(message = "Fill in price")
    @Min(value = 1, message = "Price must be greater than or equal to 1")
    private Integer price;

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
}
