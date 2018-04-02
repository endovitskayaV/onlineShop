package ru.reksoft.onlineShop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacteristicDto {
    private long id;
    private String name;
    private String type;
//    //!!!!!!!!!!!
//    @JoinTable(name = "characteristic_item")
//    private String value;

//    //!!!!!!!!!!!
//    @JoinTable(name = "category_characteristic",
//    joinColumns = @JoinColumn(name = "is_required"))
//    private boolean isRequired;
}
