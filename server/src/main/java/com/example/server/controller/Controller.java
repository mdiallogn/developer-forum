package com.example.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping("/home")
    public String homePage(){
        return  "Home page !";
    }

    @GetMapping("/admin")
    public String adminHome(){
        return "Admin page";
    }
}
