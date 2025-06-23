package com.finscope.fraudscope.authorization.rolepermission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.authorization.rolepermission.entity.RolePermission;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

}
