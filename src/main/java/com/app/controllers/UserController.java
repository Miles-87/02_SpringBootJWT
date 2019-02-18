package com.app.controllers;

import com.app.model.User;
import com.app.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    {
        "username": "u",
        "password": "1234",
        "role": "USER"
    }
    */
    @PostMapping
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }
}
