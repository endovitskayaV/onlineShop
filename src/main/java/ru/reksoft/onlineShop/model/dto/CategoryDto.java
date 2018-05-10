package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.validating.alphabeticNumericData.AlphabeticNumericData;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Data transfer object for CategoryEntity
 *
 * @see CategoryEntity
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private long id;

    @NotBlank(message = "Name must contain at least one not blank character")
    @AlphabeticNumericData
    private String name;

    @AlphabeticNumericData
    private String description;

    /**
     * category popularity
     */
    @NotNull(message = "Fill in rating")
    @Min(value = 0, message = "Rating must be greater than or equal to 0")
    private Integer rating;

    /**
     * Characteristics that belongs to this category
     * eg: Category=smartphone, characteristics={diagonal, color, weight, etc}
     */
    @NotNull(message = "Choose at least one characteristic")
    private List<CharacteristicDto> characteristics;
}
