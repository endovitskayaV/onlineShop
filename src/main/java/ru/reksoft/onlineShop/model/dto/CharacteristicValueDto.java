package ru.reksoft.onlineShop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "code")
public class CharacteristicValueDto {
    private String name;
    private String code;
    private List<String> values;

}
