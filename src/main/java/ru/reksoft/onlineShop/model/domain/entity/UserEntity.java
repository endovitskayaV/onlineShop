package ru.reksoft.onlineShop.model.domain.entity;

import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.RoleEntity;
import javax.persistence.*;

/**
 * Class represents user with email, password
 * and personal data that is used while ordering
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_info")
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    /**
     * eg: seller, customer, etc
     *
     * @see RoleEntity
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "parental_name")
    private String parentalName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;
}
