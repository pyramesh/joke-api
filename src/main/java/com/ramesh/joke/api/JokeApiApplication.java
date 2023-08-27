package com.ramesh.joke.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JokeApiApplication {


    public static void main(final String[] args) {
        SpringApplication.run(JokeApiApplication.class, args);
    }

}
