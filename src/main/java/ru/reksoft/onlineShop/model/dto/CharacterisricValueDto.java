package ru.reksoft.onlineShop.model.dto;

import lombok.*;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterisricValueDto {
    private long id;
    private String value;

}
