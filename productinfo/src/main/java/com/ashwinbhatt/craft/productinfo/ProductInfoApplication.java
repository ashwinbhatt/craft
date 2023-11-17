package com.ashwinbhatt.craft.productinfo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProductInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductInfoApplication.class, args);
    }
}
