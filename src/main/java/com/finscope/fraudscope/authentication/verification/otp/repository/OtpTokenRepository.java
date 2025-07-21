package com.finscope.fraudscope.authentication.verification.otp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long>{

	Optional<OtpToken> findByOtpVerificationTokenAndTokenStatus(String otpVerificationToken,TokenStatus tokenStatus);
	
	List<OtpToken> findAllByExpiryDateTimeBeforeAndTokenStatusNot(LocalDateTime dateTime,TokenStatus tokenStatus);
	
	List<OtpToken> findAllByTokenStatusAndExpiryDateTimeBefore(TokenStatus tokenStatus , LocalDateTime dateTime);
 	
}
