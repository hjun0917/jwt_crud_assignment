package com.sparta.basic_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class BasicCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicCrudApplication.class, args);
    }

}