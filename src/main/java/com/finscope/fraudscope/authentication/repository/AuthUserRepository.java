package com.finscope.fraudscope.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authentication.entity.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
	Optional<AuthUser> findByUsername(String username);

	Optional<AuthUser> findByEmail(String email);

	@Query(value = "SELECT * FROM auth_users WHERE username = :usernameOrEmail OR email = :usernameOrEmail", nativeQuery = true)
	Optional<AuthUser> findByUsernameOrEmail(String usernameOrEmail);

}
