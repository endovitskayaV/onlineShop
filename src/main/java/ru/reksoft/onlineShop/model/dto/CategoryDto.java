package ru.reksoft.onlineShop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private long id;
    private String name;
    private String description;
    private int rating;
    private Map<CharacteristicDto, Boolean> characteristicRequiredMap;
}
