package ru.reksoft.onlineShop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.reksoft.onlineShop.model.domain.entity.CategoryEntity;

import java.util.List;

/**
 * Data transfer object for CategoryEntity
 *
 * @see CategoryEntity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private long id;
    private String name;
    private String description;

    /**
     * category popularity
     */
    private int rating;

    /**
     * Characteristics that belongs to this category
     * eg: Category=smartphone, characteristics={diagonal, color, weight, etc}
     */
    private List<CharacteristicDto> characteristics;
}
