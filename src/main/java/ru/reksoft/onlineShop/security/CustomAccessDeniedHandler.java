package ru.reksoft.onlineShop.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String queryString = request.getQueryString();
        queryString = (queryString != null && queryString.length() > 0) ? "?" + request.getQueryString() : "";

        response.sendRedirect("/login?destination=" + request.getRequestURI() + queryString);
    }
}
