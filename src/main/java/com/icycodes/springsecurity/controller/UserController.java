package com.icycodes.springsecurity.controller;

import com.icycodes.springsecurity.entity.User;
import com.icycodes.springsecurity.model.UserModel;
import com.icycodes.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody  UserModel userModel){
        User user = userService.registerUser(userModel);
        return "Success";
    }


}
