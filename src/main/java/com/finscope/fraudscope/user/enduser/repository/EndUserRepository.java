package com.finscope.fraudscope.user.enduser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.user.enduser.entity.EndUser;

@Repository
public interface EndUserRepository extends JpaRepository<EndUser, Long> {
	
	Optional<EndUser> findByAuthUserId(Long id);
	
	

}
