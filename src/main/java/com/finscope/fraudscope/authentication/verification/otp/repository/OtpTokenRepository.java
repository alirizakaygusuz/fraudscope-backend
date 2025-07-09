package com.finscope.fraudscope.authentication.verification.otp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long>{

}
