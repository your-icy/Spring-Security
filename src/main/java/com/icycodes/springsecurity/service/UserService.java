package com.icycodes.springsecurity.service;

import com.icycodes.springsecurity.entity.User;
import com.icycodes.springsecurity.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);
}
