package ru.reksoft.onlineShop.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Builder
public class CharacteristicDto {
    private long id;
    private String name;
    private String type;
    private List<CategoryDto> categoryDtoList;


//    //!!!!!!!!!!!
//    @JoinTable(name = "characteristic_item")
//    private String value;

//    //!!!!!!!!!!!
//    @JoinTable(name = "category_characteristic",
//    joinColumns = @JoinColumn(name = "is_required"))
//    private boolean isRequired;
}
