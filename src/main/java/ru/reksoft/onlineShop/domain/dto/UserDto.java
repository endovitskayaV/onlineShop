package ru.reksoft.onlineShop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private long id;
    private String email;
    private String password;
    private RoleDto role;
    private String name;
    private String surname;
    private String parentalName;
    private String address;
    private String phoneNumber;
}
