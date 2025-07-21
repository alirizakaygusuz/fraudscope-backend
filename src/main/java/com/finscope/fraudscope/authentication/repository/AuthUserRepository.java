package com.finscope.fraudscope.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authentication.entity.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
	
	@Query(value = "SELECT * FROM auth_users WHERE username = :username", nativeQuery = true)
	Optional<AuthUser> findByUsername(String username);

	@Query(value = "SELECT * FROM auth_users WHERE email = :email ", nativeQuery = true)
	Optional<AuthUser> findByEmail(String email);

	@Query(value = "SELECT * FROM auth_users WHERE username = :usernameOrEmail OR email = :usernameOrEmail", nativeQuery = true)
	Optional<AuthUser> findByUsernameOrEmail(String usernameOrEmail);
	
	@Query("""
		    SELECT u FROM AuthUser u
		    JOIN FETCH u.userRoles ur
		    JOIN FETCH ur.role r
		    JOIN FETCH r.rolePermissions rp
		    JOIN FETCH rp.permission
		    WHERE u.username = :username
		""")
		Optional<AuthUser> findWithRolesAndPermissionsByUsername(@Param("username") String username);


}
