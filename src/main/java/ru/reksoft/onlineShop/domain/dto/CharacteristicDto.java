package ru.reksoft.onlineShop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


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
