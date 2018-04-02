package ru.reksoft.onlineShop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditableItemDto {

    private long id;
    private String name;
    private String producer;
    private int storage;
    private String description;
    private int price;
    private long categoryId;
}
