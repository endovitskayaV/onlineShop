package ru.reksoft.onlineShop.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/resources/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/resources/static/js/");
    }
}
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**").addResourceLocations("/src/main/resources");
//        registry.addResourceHandler("/css/**").addResourceLocations("/src/main/resources/static/css");
//        registry.addResourceHandler("/js/**").addResourceLocations("/src/main/resources/static/js");
//    }
