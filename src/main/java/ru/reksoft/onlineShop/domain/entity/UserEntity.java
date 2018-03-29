package ru.reksoft.onlineShop.domain.entity;

import lombok.Builder;
import lombok.Data;
import ru.reksoft.onlineShop.domain.Role;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "user_info")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated
    private Role role;

    @Column(name="name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name="parental_name")
    private String parentalName;

    @Column(name="address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

//    @OneToMany(targetEntity = , mappedBy = "user_info")
//    private List<OrderEntity> orderList;
}
