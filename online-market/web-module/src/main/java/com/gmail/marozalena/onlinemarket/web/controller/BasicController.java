package com.gmail.marozalena.onlinemarket.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
