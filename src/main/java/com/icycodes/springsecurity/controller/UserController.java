package com.icycodes.springsecurity.controller;

import com.icycodes.springsecurity.entity.User;
import com.icycodes.springsecurity.event.RegistrationCompleteEvent;
import com.icycodes.springsecurity.model.UserModel;
import com.icycodes.springsecurity.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody  UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));

        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String validateVerificationToken(@RequestParam String token ){
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "user verified successfully";
        }
        return "bad user";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +  request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath();
    }


}
