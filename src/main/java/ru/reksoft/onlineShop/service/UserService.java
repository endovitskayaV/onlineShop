package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.entity.RoleEntity;
import ru.reksoft.onlineShop.model.domain.entity.UserEntity;
import ru.reksoft.onlineShop.model.domain.repository.RoleRepository;
import ru.reksoft.onlineShop.model.domain.repository.UserRepository;
import ru.reksoft.onlineShop.model.dto.SignupUserDto;
import ru.reksoft.onlineShop.model.dto.UserDto;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    public static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        UserDetails u = new User(userEntity.getEmail(), userEntity.getPassword(), getAuthority(userEntity.getRole().getName()));
        return new User(userEntity.getEmail(), userEntity.getPassword(), getAuthority(userEntity.getRole().getName()));
    }

    private List<SimpleGrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + role.toUpperCase()));
    }

    public boolean add(SignupUserDto signupUserDto) {
        if (userRepository.findByEmail(signupUserDto.getEmail()) != null) {
            return false;
        } else {
            UserEntity userEntity = UserEntity.builder()
                    .email(signupUserDto.getEmail())
                    .password(passwordEncoder.encode(signupUserDto.getPassword()))
                    .id(userRepository.count() + 1)
                    .role(roleRepository.getOne(signupUserDto.getRoleId())).build();
            userRepository.save(userEntity);
            return true;
        }
    }

    public UserDto getByEmail(String email) {
        return toDto(userRepository.findByEmail(email));
    }

    public RoleEntity getRoleByEmail(String email){
        return userRepository.findByEmail(email).getRole();
    }
    private UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roleId(userEntity.getRole().getId())
                .name(userEntity.getName())
                .parentalName(userEntity.getParentalName())
                .surname(userEntity.getSurname())
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .build();
    }
}
