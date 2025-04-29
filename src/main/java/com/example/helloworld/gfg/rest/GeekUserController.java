package com.example.helloworld.gfg.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class GeekUserController {
    @Autowired
    private GeekUserService userService;

    // To get all geekusers
    @RequestMapping("/")
    public List<GeekUserRecord> getAllUser() {
        return userService.getAllGeekUsers();
    }

    // To add a geekuser, this is needed
    @RequestMapping(value = "/add-geekuser", method = RequestMethod.POST)
    public void addUser(@RequestBody GeekUserRecord userRecord) {
        userService.addGeekUser(userRecord);
    }
}
