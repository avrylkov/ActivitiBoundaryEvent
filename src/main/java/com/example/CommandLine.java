package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLine implements CommandLineRunner {

    @Autowired
    private DemoService demoService;

    public void run(String... args) {
        if ("test".equals(args[0])) {
            demoService.startTest();
        } else if ("develop".equals(args[0])) {
            demoService.startDevelop();
        }
    }
}
