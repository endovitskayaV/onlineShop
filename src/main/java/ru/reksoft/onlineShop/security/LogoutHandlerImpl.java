package ru.reksoft.onlineShop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutHandlerImpl implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res, Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
