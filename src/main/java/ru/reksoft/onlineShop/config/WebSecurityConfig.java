package ru.reksoft.onlineShop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.reksoft.onlineShop.security.CustomAccessDeniedHandler;
import ru.reksoft.onlineShop.security.LogoutHandlerImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**",
                        "/login","/signup",
                        "/", "/items","/items/{\\d+}", "/items/filter", "/items/search",
                        "/basket/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .addLogoutHandler(new LogoutHandlerImpl())
                .logoutSuccessUrl("/items").permitAll()
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
