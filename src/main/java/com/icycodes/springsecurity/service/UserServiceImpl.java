package com.icycodes.springsecurity.service;

import com.icycodes.springsecurity.entity.User;
import com.icycodes.springsecurity.model.UserModel;
import com.icycodes.springsecurity.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(UserModel userModel) {

        User user = User.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .role("User")
                .build();

        user = userRepository.save(user);
        log.info("User saved in db");
        return user;
    }
}
