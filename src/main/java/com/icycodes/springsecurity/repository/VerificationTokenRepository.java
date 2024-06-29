package com.icycodes.springsecurity.repository;

import com.icycodes.springsecurity.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {


}
