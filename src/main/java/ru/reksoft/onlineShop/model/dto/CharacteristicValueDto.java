package ru.reksoft.onlineShop.model.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "code")
@Builder
public class CharacteristicValueDto {
    private String name;
    private String code;
    private List<String> values;

}
