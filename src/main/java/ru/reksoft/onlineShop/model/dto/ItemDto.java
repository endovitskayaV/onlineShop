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
public class ItemDto {

    private long id;
    private String name;
    private String producer;
    private int storage;
    private String description;
    private int price;
    private CategoryDto category;

    /**
     *
     */
    private Map<CharacteristicDto, String> characteristicValueMap;
}
