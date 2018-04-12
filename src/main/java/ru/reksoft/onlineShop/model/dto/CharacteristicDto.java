package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;

import javax.validation.constraints.NotBlank;

/**
 * Data transfer object for CharacteristicEntity
 *
 * @see CharacteristicEntity
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacteristicDto {
    private long id;

    @NotBlank(message = "Name must contain at least one not blank character")
    private String name;

    /**
     * eg: metres, gramms, etc
     */
    @NotBlank(message = "Type must contain at least one not blank character")
    private String type;

  //  @NotBlank(message = "Value must contain at least one not blank character")
    private String value;

  //  private boolean required;
}
