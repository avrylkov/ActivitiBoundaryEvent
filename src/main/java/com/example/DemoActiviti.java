package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.Locale;

@SpringBootApplication
@ImportResource("classpath:activiti.cfg.xml")
public class DemoActiviti {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(DemoActiviti.class, args);
    }
}

