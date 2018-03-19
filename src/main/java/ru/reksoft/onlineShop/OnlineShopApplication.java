package ru.reksoft.onlineShop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("ru.reksoft.onlineShop")
@SpringBootApplication
public class OnlineShopApplication {

    public static void main(String[] args){
        SpringApplication.run(OnlineShopApplication.class, args);
    }
}
