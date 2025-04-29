package com.example.helloworld.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    /**
     * This will collect the logs from this class and show them in the console
     *  because of static we don't need to instantiate this class it will call this logger directly.
     */
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    /// This will load the log
    static {
        log.info("AuthController class loaded!"); // ← this will log without needing an instance
    }
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
//    public ResponseEntity<String> signup(@RequestBody String request, @RequestHeader()) {
//        try {
//
//        } catch (Exception e) {
//
//        }
//    }
private record Greeting(long id, String content) { }
}

//cost calculator -> ERP, E-commerce, Digital branding