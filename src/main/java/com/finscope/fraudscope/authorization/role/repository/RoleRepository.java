package com.finscope.fraudscope.authorization.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authorization.role.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
