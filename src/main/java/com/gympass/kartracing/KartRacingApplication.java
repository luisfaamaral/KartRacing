package com.gympass.kartracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class KartRacingApplication {

    public static void main(String[] args) {
        SpringApplication.run(KartRacingApplication.class, args);
    }

}
