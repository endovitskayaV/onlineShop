package ru.reksoft.onlineShop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //indicate where images are stored in file system
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:src/main/resources/static/img/");
    }
}

