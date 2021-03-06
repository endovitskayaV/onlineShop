package ru.reksoft.onlineShop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.reksoft.onlineShop.model.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.service.CategoryService;
import ru.reksoft.onlineShop.validating.alphabeticNumericData.AlphabeticNumericData;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Data transfer object for CategoryEntity
 * it is used while constructing new category
 * NewCategoryDto does not have id
 * as it is generated while adding in CategoryService
 *
 * @see CategoryEntity
 * @see CategoryService
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCategoryDto {

    @NotBlank(message = "Name must contain at least one not blank character")
    @AlphabeticNumericData
    private String name;

    @NotBlank(message = "Description must contain at least one not blank character")
    @AlphabeticNumericData
    private String description;

    /**
     * category popularity
     */
    @NotNull(message = "Fill in rating")
    @Min(value = 0, message = "Rating must be greater than or equal to 0")
    @Max(value = Integer.MAX_VALUE, message ="Rating must be less than or equal to "+Integer.MAX_VALUE )
    private Integer rating;

    /**
     * ids of characteristics that belongs to this category
     * eg: Category=smartphone, characteristics={diagonal, color, weight, etc}
     * only ids, not full CharacteristicDtos
     * as it is more convenient to send only id from web interface
     */
    @NotNull(message = "Choose at least one characteristic")
    List<Long> characteristicIds;
}
