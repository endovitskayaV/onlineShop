package ru.reksoft.onlineShop.domain.dto;

import lombok.Builder;
import lombok.Data;
import ru.reksoft.onlineShop.domain.Role;

import javax.persistence.*;

@Data
@Builder
public class UserDto {
    private long id;
    private String email;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private String parentalName;
    private String address;
    private String phoneNumber;
}
