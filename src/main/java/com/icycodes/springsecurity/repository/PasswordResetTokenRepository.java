package com.icycodes.springsecurity.repository;

import com.icycodes.springsecurity.entity.PasswordResetToken;
import com.icycodes.springsecurity.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {


    PasswordResetToken findByToken(String token);
}