package ru.reksoft.onlineShop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    private long id;
    private String name;
    private int storage;
    private int price;
    private CategoryDto categoryDto;
    private List<CharacteristicDto> characteristicDtoList;
}
