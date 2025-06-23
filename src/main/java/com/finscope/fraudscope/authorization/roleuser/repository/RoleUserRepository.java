package com.finscope.fraudscope.authorization.roleuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authorization.roleuser.entity.RoleUser;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {

}
