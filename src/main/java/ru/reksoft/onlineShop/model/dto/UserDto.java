package ru.reksoft.onlineShop.model.dto;

import lombok.*;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private long id;
    private String email;
    private String password;
    private long roleId;
    private String name;
    private String surname;
    private String parentalName;
    private String address;
    private String phoneNumber;

}



