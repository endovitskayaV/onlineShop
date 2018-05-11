package ru.reksoft.onlineShop.config;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.reksoft.onlineShop.security.CustomAccessDeniedHandler;
import ru.reksoft.onlineShop.security.LogoutHandlerImpl;
import ru.reksoft.onlineShop.security.LogoutSuccessHandlerImpl;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**",
                        "/login","/signup",
                        "/", "/items","/items/{\\d+}", "/items/filter", "/items/search",
                        "/characteristics*",
                        "/basket", "/basket/add/**", "/basket/edit/**", "/basket/delete/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .addLogoutHandler(new LogoutHandlerImpl())
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAccessDeniedHandler());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
