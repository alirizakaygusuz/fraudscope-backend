package com.finscope.fraudscope.user.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finscope.fraudscope.user.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	
	Optional<Admin> findByAuthUserId(Long id);

	
}
