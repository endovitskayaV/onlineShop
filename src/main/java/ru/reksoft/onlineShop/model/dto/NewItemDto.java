package ru.reksoft.onlineShop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

/*
* ItemDto class that is used for creation new item
* does not contain id, because it is generated in service before inserting to db
*/
public class NewItemDto {
    private String name;
    private String producer;
    private int storage;
    private String description;
    private int price;
    private long categoryId;
    private List<CharacteristicDto> characteristics;
}
