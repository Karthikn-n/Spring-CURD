package com.example.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurdController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring";
    }
}
