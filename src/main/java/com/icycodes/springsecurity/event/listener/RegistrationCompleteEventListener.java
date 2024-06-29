package com.icycodes.springsecurity.event.listener;


import com.icycodes.springsecurity.entity.User;
import com.icycodes.springsecurity.event.RegistrationCompleteEvent;
import com.icycodes.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        //sending email to user


    }




}
