package com.finscope.fraudscope.authentication.refreshtoken.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	int deleteByExpiryDateBefore(LocalDateTime now);
	
	List<RefreshToken> findAllByExpiryDateBeforeAndRevokedFalse(LocalDateTime now);
	
	Optional<RefreshToken> findByToken(String token);

	List<RefreshToken> findAllByRevokedTrueAndRevokedAtBefore(LocalDateTime localDateTime);
	
	
	@Modifying
	@Query(value =  """
			UPDATE refresh_tokens rt
			JOIN auth_users au ON rt.auth_user_id = au.id
			SET rt.revoked = true,
			    rt.revoked_at = CURRENT_TIMESTAMP
			WHERE rt.auth_user_id = :authUserId
			  AND rt.revoked = false
			""", nativeQuery = true)
	void revokeValidRefreshTokenForAuthUser(@Param("authUserId") Long authUserId);

	
	
}
