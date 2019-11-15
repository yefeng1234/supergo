package com.supergo.user.controller;

import com.supergo.user.domain.User;
import com.supergo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by on 2019/5/29.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //注入service
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public User createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
            return userService.create(username, password);

    }

}
