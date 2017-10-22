package com.main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.api",
        "com.controllers",
        "com.dao"})
public class Launcher {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Launcher.class, args);
    }
}
