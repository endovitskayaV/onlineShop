package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private int storage;
    private String description;

    @NotNull(message = "Fill in price")
    @Min(value = 0, message = "Price must be greater than or equal to 1")
    private int price;

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
    private List<CharacteristicDto> characteristics;
}
