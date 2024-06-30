package com.icycodes.springsecurity.controller;

import com.icycodes.springsecurity.entity.PasswordResetToken;
import com.icycodes.springsecurity.entity.User;
import com.icycodes.springsecurity.entity.VerificationToken;
import com.icycodes.springsecurity.event.RegistrationCompleteEvent;
import com.icycodes.springsecurity.model.PasswordModel;
import com.icycodes.springsecurity.model.UserModel;
import com.icycodes.springsecurity.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Log4j2
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

    @GetMapping("/resendVerifyToken")
    public String resendVerificationTokenToUser(
            @RequestParam("token") String oldToken,
            HttpServletRequest request
    ){
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request),verificationToken);
        return "Success";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +  request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath();
    }


    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken){
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        log.info(" url to hit : {}" + url);
    }


    @PostMapping("/password/reset")
    public String passwordReset(@RequestBody PasswordModel passwordModel, final HttpServletRequest request){
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if(user != null){
            String token = UUID.randomUUID().toString();
            userService.sendPasswordResetTokenToUser(token , user);
            String url = passwordResetTokenMail(user, applicationUrl(request), token);
        }

        return "Success";
    }

    @PostMapping("/savePassword")
    public String saveUserPassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){
        String result = userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")){
            return "Invalid request, Kindly try it again";
        }
        return "good boi";

    }



    private String passwordResetTokenMail(User user, String applicationUrl, String resetToken){
        String url = (applicationUrl + "/resetPassword?token=" + resetToken);
        log.info((" url to hit : {}" + url));
        return "token mail sent";
    }


}
