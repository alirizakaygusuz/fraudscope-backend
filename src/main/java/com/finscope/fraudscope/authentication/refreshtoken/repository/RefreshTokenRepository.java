package com.finscope.fraudscope.authentication.refreshtoken.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	int deleteByExpiryDateBefore(LocalDateTime now);
	
	List<RefreshToken> findAllByExpiryDateBeforeAndRevokedFalse(LocalDateTime now);
	
	
}
