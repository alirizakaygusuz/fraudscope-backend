package com.finscope.fraudscope.authorization.rolepermission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authorization.permission.entity.Permission;
import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.authorization.rolepermission.entity.RolePermission;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

	Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission);
	

}
