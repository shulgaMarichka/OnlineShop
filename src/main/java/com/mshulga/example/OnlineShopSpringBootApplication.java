package com.mshulga.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.mshulga.example.dao.imp",
        "com.mshulga.example.service",
        "com.mshulga.example.controller"})
public class OnlineShopSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopSpringBootApplication.class, args);
    }

}
