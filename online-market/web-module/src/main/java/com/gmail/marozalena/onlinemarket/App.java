package com.gmail.marozalena.onlinemarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {
        "com.gmail.marozalena.onlinemarket.repository",
        "com.gmail.marozalena.onlinemarket.service",
        "com.gmail.marozalena.onlinemarket.web"},
        exclude = UserDetailsServiceAutoConfiguration.class)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
