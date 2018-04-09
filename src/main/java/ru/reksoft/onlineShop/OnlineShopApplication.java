package ru.reksoft.onlineShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * main class
 * manages run configurations
 */
@SpringBootApplication
public class OnlineShopApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OnlineShopApplication.class);
    }

    /**
     * Runs whole application
     */
    public static void main(String[] args){
        SpringApplication.run(OnlineShopApplication.class, args);
    }
}