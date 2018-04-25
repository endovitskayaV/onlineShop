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
import ru.reksoft.onlineShop.security.AuthEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthEntryPoint authEntryPoint;

    @Autowired
    public WebSecurityConfig(AuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/src/main/resources/**");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http
//                .authorizeRequests()
//                .antMatchers("/login", "/items/**").permitAll()
//                .anyRequest().authenticated();
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();

        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/login").permitAll();
                //.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll();
        //.authorizeRequests().antMatchers("/login*").anonymous().anyRequest().permitAll();
// .cors().and().csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
////                .and()
////                .headers().disable()
//               .and()



//                .authorizeRequests()
//                .antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**",
//                        "/login/**", "/signup/**",
//                        "/basket/**",
//                        "/", "/items", "items/filter", "items/search", "items/{id}")
//                .permitAll()
//                .anyRequest().authenticated();




//                .and()
//                .exceptionHandling().accessDeniedHandler(authEntryPoint);//.authenticationEntryPoint(authEntryPoint);
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
