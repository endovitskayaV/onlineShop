package ru.reksoft.onlineShop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
        String href = request.getHeader("Referer");
        if (href.contains("items") || href.contains("basket") ){ //only logout on items page and basket  will redirect to this page
            response.sendRedirect(href);
        }else{
            response.sendRedirect("/items"); //other pages are forbidden for unauthorised user-> redirect to main page
        }

        super.onLogoutSuccess(request, response, authentication);
    }
}
