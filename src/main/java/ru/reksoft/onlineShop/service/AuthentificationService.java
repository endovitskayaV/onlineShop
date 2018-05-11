package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static ru.reksoft.onlineShop.service.UserService.ROLE_PREFIX;

@Service
public class AuthentificationService {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthentificationService(UserService userService,AuthenticationManager authenticationManager){
        this.userService=userService;
        this.authenticationManager=authenticationManager;
    }

    public boolean login(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(email, password,
                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + userService.getRoleByEmail(email).getName().toUpperCase())));
            Authentication authentication = authenticationManager.authenticate(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
