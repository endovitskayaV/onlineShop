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
import ru.reksoft.onlineShop.security.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public WebSecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/src/main/resources/**");
//    }

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
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        //.accessDeniedHandler()
               // .accessDeniedPage("/accessDenied.jsp");;
        //http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll(); //.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        //  http.cors().and().csrf().disable().authorizeRequests().antMatchers("/login").permitAll();
        //   http.formLogin().loginPage("/login").permitAll().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
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
