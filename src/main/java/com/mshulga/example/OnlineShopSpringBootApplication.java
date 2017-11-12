package com.mshulga.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mshulga.example"})
@EnableElasticsearchRepositories(basePackages = "com.mshulga.example.dao.es")
@EnableJpaRepositories(basePackages = "com.mshulga.example.dao.jpa")
public class OnlineShopSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopSpringBootApplication.class, args);
    }

}
